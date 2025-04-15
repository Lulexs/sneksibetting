package com.luka.sneksibetting.services;
import com.luka.sneksibetting.models.gameMessages.AckMessage;
import com.luka.sneksibetting.models.gameMessages.SnakeMoveMessage;
import com.luka.sneksibetting.models.gameMessages.UpdateGameStateMessage;
import com.luka.sneksibetting.models.snake.GameState;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.Executors;
import org.springframework.web.socket.BinaryMessage;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameUpdateService {

    private final HashMap<String, HashSet<WebSocketSession>> gamesToUsers;
    private final GameService gameService;
    private final HashMap<String, ArrayList<SnakeMoveMessage>> moves;
    private final HashMap<String, Boolean[]> acks;
    public GameUpdateService(HashMap<String, Boolean[]> acks, HashMap<String, ArrayList<SnakeMoveMessage>> moves, GameService gameService, HashMap<String, HashSet<WebSocketSession>> gamesToUsers) {
        this.gamesToUsers = gamesToUsers;
        this.gameService = gameService;
        this.moves = moves;
        this.acks = acks;
    }

    public void Ack(AckMessage msg, Integer msgType) {
        if (msgType == 5) {
            gameService.Switcheroo(msg.getGameId());
        }
        Boolean[] acksB = acks.get(msg.getGameId());
        if (msg.getPlayer1()) {
            acksB[0] = true;
        } else  {
            acksB[1] = true;
        }

        if (acksB[0] && acksB[1]) {
            acksB[0] = acksB[1] = false;
            HashSet<WebSocketSession> entry = gamesToUsers.get(msg.getGameId());
            UpdateGameStateMessage updateMessage = buildGameUpdate(msg.getGameId());
            try {
                BinaryMessage binaryMessage = new BinaryMessage(updateMessage.GetBytes());
                for (WebSocketSession session : entry) {
                    session.sendMessage(binaryMessage);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private UpdateGameStateMessage buildGameUpdate(String gameId) {
        GameState gameState = gameService.ReadGameStateFromRedis(gameId);
        gameService.PrevState(gameState);

        ArrayList<SnakeMoveMessage> moves = this.moves.get(gameId);
        this.moves.put(gameState.getGameId(), new ArrayList<>());

        for (SnakeMoveMessage moveMessage : moves) {
            if (moveMessage.getPlayer1()) {
                gameState.getPlayer1Board().ChangeDir(moveMessage.getDirection());
            }
            else {
                gameState.getPlayer2Board().ChangeDir(moveMessage.getDirection());
            }
        }

        gameState.getPlayer1Board().Update();
        gameState.getPlayer2Board().Update();

        gameService.AddGameStateToRedis(gameState);

        return new UpdateGameStateMessage(gameId, gameState.getNumWatching(), gameState.getPlayer1Username(), gameState.getPlayer2Username(),
                gameState.getPlayer1Board(), gameState.getPlayer2Board());
    }
}
