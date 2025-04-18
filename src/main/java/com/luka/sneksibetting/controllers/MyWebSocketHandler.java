package com.luka.sneksibetting.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luka.sneksibetting.models.gameMessages.*;
import com.luka.sneksibetting.models.snake.GameState;
import com.luka.sneksibetting.services.GameService;
import com.luka.sneksibetting.services.GameUpdateService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class MyWebSocketHandler extends BinaryWebSocketHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final HashMap<String, WebSocketSession> websockets = new HashMap<>();
    private static final HashMap<String, HashSet<WebSocketSession>> gamesToUsers = new HashMap<>();
    private final GameService gameService;
    private final HashMap<String, Boolean[]> acks = new HashMap<>();
    private final GameUpdateService gameUpdateService;

    private static final HashMap<String, ArrayList<SnakeMoveMessage>> moves = new HashMap<>();

    public MyWebSocketHandler(GameService gameService) {
        this.gameService = gameService;
        gameUpdateService = new GameUpdateService(acks, moves, gameService, gamesToUsers);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connected: " + session.getId());
    }

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        ByteBuffer buffer = message.getPayload();
        int type = buffer.getInt();

        byte[] jsonBytes = new byte[buffer.remaining()];
        buffer.get(jsonBytes);
        String jsonString = new String(jsonBytes, StandardCharsets.UTF_8);

//        System.out.println(type + jsonString);
        switch (type) {
            case 1:
                HelloMessage hello = objectMapper.readValue(jsonString, HelloMessage.class);
                websockets.put(hello.getUserId(), session);
                gameService.AddUserToQueue(hello);
                session.sendMessage(new BinaryMessage(new QueuedMessage().GetBytes()));
                Optional<AbstractMap.SimpleEntry<String, String>> pairing = this.gameService.TryPair();
                if (pairing.isPresent()) {
                    GameStartNoBetMessage gameStartNoBetMessage = this.gameService.SchedGame(pairing.get().getKey(), pairing.get().getValue());
                    WebSocketSession sesh1 = websockets.get(pairing.get().getKey());
                    WebSocketSession sesh2 = websockets.get(pairing.get().getValue());
                    gamesToUsers.put(gameStartNoBetMessage.getGameId(), new HashSet<>());
                    gamesToUsers.get(gameStartNoBetMessage.getGameId()).add(sesh1);
                    gamesToUsers.get(gameStartNoBetMessage.getGameId()).add(sesh2);
                    moves.put(gameStartNoBetMessage.getGameId(), new ArrayList<>());
                    acks.put(gameStartNoBetMessage.getGameId(), new Boolean[] {false, false});
                    gameService.AddGameStateToRedis(new GameState(gameStartNoBetMessage));
                    sesh1.sendMessage(new BinaryMessage(gameStartNoBetMessage.GetBytes()));
                    sesh2.sendMessage(new BinaryMessage(gameStartNoBetMessage.GetBytes()));
                }
                break;
            case 5:
                SnakeMoveMessage moveMessage = objectMapper.readValue(jsonString, SnakeMoveMessage.class);
                moves.get(moveMessage.getGameId()).add(moveMessage);
                gameUpdateService.Ack(new AckMessage(moveMessage.getGameId(), moveMessage.getPlayer1()), 5);
                break;
            case 6:
                AckMessage ackMessage = objectMapper.readValue(jsonString, AckMessage.class);
                gameUpdateService.Ack(ackMessage, 6);
            default:
                break;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        try {
            websockets.remove(session.getId());
        }
        catch (Exception ec) {
            System.out.println("Error: " + ec.getMessage());
        }
        System.out.println("Disconnected: " + session.getId() + " due to " + status.getReason());
    }
}
