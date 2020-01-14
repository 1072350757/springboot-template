package com.baronj.template.task;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class Task {

    @Scheduled(cron = "0 01 00 * * ?")
    public void dmzshequSignIn(){

    }
}
