package com.luka.sneksibetting.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luka.sneksibetting.models.gameMessages.HelloMessage;
import com.luka.sneksibetting.models.gameMessages.QueuedMessage;
import com.luka.sneksibetting.services.GameService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MyWebSocketHandler extends BinaryWebSocketHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final ConcurrentHashMap<String, String> seshToUid = new ConcurrentHashMap<>();
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
                seshToUid.put(session.getId(), hello.getUserId());
                gameService.AddUserToQueue(hello);
                session.sendMessage(new BinaryMessage(new QueuedMessage().GetBytes()));
                break;
            default:
                break;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        seshToUid.remove(session.getId());
        System.out.println("Disconnected: " + session.getId() + " due to " + status.getReason());
    }
}
