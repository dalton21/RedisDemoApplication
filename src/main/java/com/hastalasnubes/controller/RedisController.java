package com.hastalasnubes.controller;

import com.hastalasnubes.service.RedisService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController {

    private final RedisService redisService;

    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @GetMapping("/byId/{key}")
    public Object getById(@PathVariable("key") String key) {
        return redisService.getProductById(key);
    }

    @PostMapping("/{key}")
    public void set(@PathVariable("key") String key, @RequestBody String value) {
        redisService.save(key, value);
    }

    @GetMapping("/{key}")
    public Object get(@PathVariable("key") String key) {
        return redisService.get(key);
    }

    @DeleteMapping("/{key}")
    public void delete(@PathVariable("key") String key) {
        redisService.delete(key);
    }

}

