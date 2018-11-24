package com.study.js.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

/*
    내장 Redis를 프로젝트가 profile = local 일때만 실행되도록 하는 설정
 */

@Slf4j //lombok
@Profile("local") // profile이 local일때만 활성화
@Configuration
public class EmbeddedRedisConfig {

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() throws IOException {
        redisServer = new RedisServer(redisPort);
        log.info("start>>>>>>>>>>>>>>>>>>");
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            log.info("end>>>>>>>>>>>>>>>>>>");
            redisServer.stop();
        }
    }
}