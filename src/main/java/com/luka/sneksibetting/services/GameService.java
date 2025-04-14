package com.luka.sneksibetting.services;

import com.luka.sneksibetting.models.gameMessages.GameStartNoBetMessage;
import com.luka.sneksibetting.models.gameMessages.HelloMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {
    private final SetOperations<String, String> setOperations;

    public GameService(RedisTemplate<String, String> rt) {
        setOperations = rt.opsForSet();
    }

    public void AddUserToQueue(HelloMessage helloMessage) {
        setOperations.add("QUEUE", helloMessage.getUserId());
    }

    public Optional<AbstractMap.SimpleEntry<String, String>> TryPair() {
        Set<String> userIds = setOperations.members("QUEUE");
        if (userIds != null && userIds.size() >= 2) {
            Iterator<String> iterator = userIds.iterator();
            String user1 = iterator.next();
            String user2 = iterator.next();
            return Optional.of(new AbstractMap.SimpleEntry<>(user1, user2));
        }
        return Optional.empty();
    }

    public GameStartNoBetMessage SchedGame(String user1, String user2) {
        return null;
    }

    public void RemoveFromQueue(String userId) {
        setOperations.remove(userId);
    }
}
