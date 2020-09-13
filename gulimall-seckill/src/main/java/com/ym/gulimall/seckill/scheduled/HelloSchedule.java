package com.ym.gulimall.seckill.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@EnableAsync
@Slf4j
@Component
@EnableScheduling
public class HelloSchedule {

    @Async
    @Scheduled(cron = "* * * ? * 5")
    public void hello() throws InterruptedException {
       log.info("hello!");
       Thread.sleep(3000);
    }

}
