package com.hastalasnubes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RedisServiceTest {

    @Mock
    RedisTemplate<String, Object> redisTemplate;

    @Mock
    ValueOperations<String, Object> valueOps;

    RedisService redisService;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        redisService = new RedisService(redisTemplate);
    }

    @Test
    void saveAndGet() {
        when(valueOps.get("key1")).thenReturn("value1");

        redisService.save("key1", "value1");
        verify(valueOps).set("key1", "value1");

        Object v = redisService.get("key1");
        assertEquals("value1", v);
    }

    @Test
    void saveWithTtl() {
        redisService.save("k2", "v2", 5L);
        verify(valueOps).set("k2", "v2", 5L, TimeUnit.SECONDS);
    }

    @Test
    void deleteAndExists() {
        when(redisTemplate.delete("k3")).thenReturn(true);
        when(redisTemplate.hasKey("k3")).thenReturn(true);

        assertTrue(redisService.delete("k3"));
        assertTrue(redisService.exists("k3"));

        verify(redisTemplate).delete("k3");
        verify(redisTemplate).hasKey("k3");
    }

    @Test
    void getProductByIdReturnsFormattedString() {
        String id = "123";
        String result = redisService.getProductById(id);
        assertEquals("Item-" + id, result);
    }
}

