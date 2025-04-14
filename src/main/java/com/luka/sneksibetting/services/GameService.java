package com.luka.sneksibetting.services;

import com.luka.sneksibetting.models.gameMessages.HelloMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private final SetOperations<String, String> setOperations;

    public GameService(RedisTemplate<String, String> rt) {
        setOperations = rt.opsForSet();
    }

    public void AddUserToQueue(HelloMessage helloMessage) {
        setOperations.add("QUEUE", helloMessage.getUserId());
    }

    public void RemoveFromQueue(String userId) {
        setOperations.remove(userId);
    }
}
