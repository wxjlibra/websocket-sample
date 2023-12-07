package com.example.websocketdemo.service;

import com.example.websocketdemo.config.CustomChannelInterceptor;
import com.example.websocketdemo.vo.SampleObject;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

@Service
public class TargetMessageSendService {
    @Resource
    private SimpMessagingTemplate messagingTemplate;
    @Resource
    private CustomChannelInterceptor customChannelInterceptor;


//    @Scheduled(fixedRate = 10000)
    public void mimicNotifyUser() throws InterruptedException {
        System.out.println("invoked");
        Thread.sleep(10000L);
        SampleObject sampleObject = new SampleObject();
        sampleObject.setName("AttisSpecific");
        messagingTemplate.convertAndSendToUser("mockUserId2", "/queue/notifications", sampleObject);
    }

    public void notifyUserReal(String userIdentifier, String message) {

    }


}
