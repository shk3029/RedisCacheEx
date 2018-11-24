package com.study.js;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JsApplicationTests {

    @Autowired
    private PointRedisRepository pointRedisRepository;

    @After
    public void tearDown() throws Exception {
        pointRedisRepository.deleteAll();
    }
    @Test
    public void 기본_등록_조회가능() {
        //given
        String id = "js";
        LocalDateTime refreshTime = LocalDateTime.of(2018, 5, 26, 0, 0);
        Point point = Point.builder()
                .id(id)
                .amount(1000L)
                .refreshTime(refreshTime)
                .build();

        //when
        pointRedisRepository.save(point);

        //then
        Point savedPoint = pointRedisRepository.findById(id).get();
        assertThat(savedPoint.getAmount()).isEqualTo(1000L);
        assertThat(savedPoint.getRefreshTime()).isEqualTo(refreshTime);
    }

    @Test
    public void 수정기능() {

        //given
        String id = "js";
        LocalDateTime refreshTime = LocalDateTime.of(2018, 5, 26, 0, 0);
        pointRedisRepository.save(Point.builder()
                .id(id)
                .amount(1000L)
                .refreshTime(refreshTime)
                .build());

        //when
        Point savedPoint = pointRedisRepository.findById(id).get();
        savedPoint.refresh(2000L, LocalDateTime.of(2018,6,1,0,0));
        pointRedisRepository.save(savedPoint);

        //then
        Point refreshPoint = pointRedisRepository.findById(id).get();
        assertThat(refreshPoint.getAmount()).isEqualTo(2000L);
    }
}
