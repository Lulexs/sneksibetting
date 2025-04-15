package com.luka.sneksibetting.services;

import com.luka.sneksibetting.models.gameMessages.GameStartNoBetMessage;
import com.luka.sneksibetting.models.gameMessages.HelloMessage;
import com.luka.sneksibetting.models.user.User;
import com.luka.sneksibetting.repositories.UserRepository;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {
    private final SetOperations<String, String> setOperations;
    private final UserRepository userRepository;
    private final HashOperations<String, String, String> hashOperations;

    public GameService(RedisTemplate<String, String> rt, UserRepository userRepository) {
        setOperations = rt.opsForSet();
        this.userRepository = userRepository;
        hashOperations = rt.opsForHash();
    }

    public void AddUserToQueue(HelloMessage helloMessage) {
        setOperations.add("QUEUE", helloMessage.getUserId());
    }

    public void AddGameStateToRedis(GameStartNoBetMessage gameStartNoBetMessage) {
        try {
            hashOperations.put("GAMES", gameStartNoBetMessage.getGameId(), gameStartNoBetMessage.toZson());
        }
        catch (Exception ec) {
            System.out.println(ec.getMessage());
        }
    }

    public Optional<AbstractMap.SimpleEntry<String, String>> TryPair() {
        Set<String> userIds = setOperations.members("QUEUE");
        if (userIds != null && userIds.size() >= 2) {
            Iterator<String> iterator = userIds.iterator();
            String user1 = iterator.next();
            String user2 = iterator.next();
            setOperations.remove("QUEUE", user1);
            setOperations.remove("QUEUE", user2);
            return Optional.of(new AbstractMap.SimpleEntry<>(user1, user2));
        }
        return Optional.empty();
    }

    public GameStartNoBetMessage SchedGame(String user1Id, String user2Id) {
        User user1 = userRepository.getUserByUserId(UUID.fromString(user1Id));
        User user2 = userRepository.getUserByUserId(UUID.fromString(user2Id));
        return new GameStartNoBetMessage(user1.username(), user2.username(), UUID.randomUUID().toString(),
                user1.elo(), user2.elo());
    }

    public void RemoveFromQueue(String userId) {
        setOperations.remove(userId);
    }
}
