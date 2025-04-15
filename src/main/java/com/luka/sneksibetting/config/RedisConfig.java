package com.luka.sneksibetting.config;

import com.luka.sneksibetting.models.gameMessages.GameStartNoBetMessage;
import com.luka.sneksibetting.models.gameMessages.HelloMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, HelloMessage> helloMessageRedisTemplate() {
        RedisTemplate<String, HelloMessage> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, GameStartNoBetMessage> gameStateMessage() {
        RedisTemplate<String, GameStartNoBetMessage> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }
}
