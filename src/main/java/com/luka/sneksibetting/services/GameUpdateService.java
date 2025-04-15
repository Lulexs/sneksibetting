package com.luka.sneksibetting.services;
import com.luka.sneksibetting.models.gameMessages.UpdateGameStateMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import org.springframework.web.socket.BinaryMessage;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class GameUpdateService {

//    private final ConcurrentHashMap<String, Set<WebSocketSession>> gamesToUsers;
//
//    public GameUpdateService(ConcurrentHashMap<String, Set<WebSocketSession>> gamesToUsers) {
//        this.gamesToUsers = gamesToUsers;
//
//        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
//        scheduler.scheduleAtFixedRate(this::sendGameUpdates, 0, 500, TimeUnit.MILLISECONDS);
//    }

//    private void sendGameUpdates() {
//        for (Map.Entry<String, Set<WebSocketSession>> entry : gamesToUsers.entrySet()) {
//            String gameId = entry.getKey();
//            Set<WebSocketSession> sessions = entry.getValue();
//
//            UpdateGameStateMessage updateMessage = buildGameUpdate(gameId);
//
//            try {
//                BinaryMessage binaryMessage = new BinaryMessage(updateMessage.GetBytes());
//
//                for (WebSocketSession session : sessions) {
//                    session.sendMessage(binaryMessage);
//                }
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        }
//    }
//
//    private UpdateGameStateMessage buildGameUpdate(String gameId) {
//
//    }
}
