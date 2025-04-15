package com.luka.sneksibetting.services;
import com.luka.sneksibetting.models.gameMessages.UpdateGameStateMessage;
import com.luka.sneksibetting.models.snake.GameState;
import org.jooq.meta.derby.sys.Sys;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import org.springframework.web.socket.BinaryMessage;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameUpdateService {

    private final HashMap<String, HashSet<WebSocketSession>> gamesToUsers;
    private final GameService gameService;

    public GameUpdateService(GameService gameService, HashMap<String, HashSet<WebSocketSession>> gamesToUsers) {
        this.gamesToUsers = gamesToUsers;
        this.gameService = gameService;

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::sendGameUpdates, 0, 500, TimeUnit.MILLISECONDS);
    }

    private void sendGameUpdates() {
        System.out.println("Sending game updates " + gamesToUsers.size());
        for (Map.Entry<String, HashSet<WebSocketSession>> entry : gamesToUsers.entrySet()) {
            String gameId = entry.getKey();
            Set<WebSocketSession> sessions = entry.getValue();


            UpdateGameStateMessage updateMessage = buildGameUpdate(gameId);

            try {
                BinaryMessage binaryMessage = new BinaryMessage(updateMessage.GetBytes());
                for (WebSocketSession session : sessions) {
                    session.sendMessage(binaryMessage);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private UpdateGameStateMessage buildGameUpdate(String gameId) {
        GameState gameState = gameService.ReadGameStateFromRedis(gameId);

        gameState.getPlayer1Board().Update();
        gameState.getPlayer2Board().Update();

        gameService.AddGameStateToRedis(gameState);

        return new UpdateGameStateMessage(gameId, gameState.getNumWatching(), gameState.getPlayer1Username(), gameState.getPlayer2Username(),
                gameState.getPlayer1Board(), gameState.getPlayer2Board());
    }
}
