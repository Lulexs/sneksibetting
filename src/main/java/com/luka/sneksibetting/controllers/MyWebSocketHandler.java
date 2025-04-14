package com.luka.sneksibetting.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luka.sneksibetting.models.gameMessages.GameStartNoBetMessage;
import com.luka.sneksibetting.models.gameMessages.HelloMessage;
import com.luka.sneksibetting.models.gameMessages.QueuedMessage;
import com.luka.sneksibetting.services.GameService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MyWebSocketHandler extends BinaryWebSocketHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final ConcurrentHashMap<String, WebSocketSession> websockets = new ConcurrentHashMap<>();
    private final GameService gameService;

    public MyWebSocketHandler(GameService gameService) {
        this.gameService = gameService;
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

        System.out.println(type + jsonString);
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
                    sesh1.sendMessage(new BinaryMessage(gameStartNoBetMessage.GetBytes()));
                    sesh2.sendMessage(new BinaryMessage(gameStartNoBetMessage.GetBytes()));
                }
                break;
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
