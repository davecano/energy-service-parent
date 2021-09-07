package com.tiansu.energy.ltc.third;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("redis测试")
@SpringBootTest
class RedisServiceTest {
    @Resource
    private RedisService redisService;

    @Test
    @DisplayName("redis操作String测试")
    public void  redisStringOperaterTest(){
        String key="test";
        redisService.set(key,"tttt");
        assertEquals("tttt",redisService.get(key));
    }

}