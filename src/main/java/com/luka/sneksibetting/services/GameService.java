package com.luka.sneksibetting.services;

import com.luka.sneksibetting.models.gameMessages.HelloMessage;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private ListOperations<String, HelloMessage> listOperations;

    public GameService(RedisTemplate<String, HelloMessage> rt) {
        listOperations = rt.opsForList();
    }

    public void AddUserToQueue(HelloMessage helloMessage) {
        listOperations.rightPush("QUEUE", helloMessage);
    }
}
