package com.hastalasnubes.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ValueOperations<String, Object> valueOps;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOps = redisTemplate.opsForValue();
    }

    public void save(String key, Object value) {
        valueOps.set(key, value);
    }

    // expiración en segundos
    public void save(String key, Object value, long ttlSeconds) {
        valueOps.set(key, value, ttlSeconds, TimeUnit.SECONDS);
    }

    public Object get(String key) {
        return valueOps.get(key);
    }

    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    //La primera vez que se llame al método con un id específico, se ejecutará el código dentro del método y el resultado se almacenará en la caché "products" con la clave correspondiente al id.
    //Las llamadas subsiguientes al método con el mismo id recuperarán el valor directamente de cache
    @Cacheable(value = "products", key = "#p0")
    public String getProductById(String id) {
        System.out.println("Buscando item...");
        return "Item-" + id;
    }


}

