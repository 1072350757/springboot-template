package com.baronj.template.task;

import com.baronj.template.service.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class Task {

    @Autowired
    private PropertiesService propertiesService;

    @Scheduled(cron = "0 00 00 * * ?")
    public void signInTask() {
        propertiesService.dmzshequSignIn();
        propertiesService.hlodaySignIn();
    }

    @Scheduled(cron = "0 00 08 * * ?")
    public void dmzshequYyy() {
        propertiesService.dmzshequYyy();
    }
}
