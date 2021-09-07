package com.tiansu.energy.ltc.module.demo;

import com.tiansu.energy.ltc.module.demo.domain.entity.Student;
import com.tiansu.energy.ltc.third.RedisService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = {"redis测试"})
@Slf4j
public class RedisController {

    @Resource
    private RedisService redisService;


    @GetMapping("/testRedis")
    public String testRedis() {
        redisService.set("hello", "world");
        return redisService.get("hello");
    }


    @GetMapping("/testCache")
    @Cacheable("studentCache")
    public Student testCache(String name) {
        log.info("query name:" + name);
        return getFormDb(name);
    }

    private Student getFormDb(String name) {
        log.info("query from DB");
        Student student = new Student();
        student.setIdCard("1234");
        student.setName(name);
        student.setAddress("南京");
        return student;
    }

}
