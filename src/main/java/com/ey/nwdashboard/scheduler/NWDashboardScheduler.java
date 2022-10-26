package com.ey.nwdashboard.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class NWDashboardScheduler {

    @Autowired
    RestTemplate restTemplate;

    @Scheduled(cron = "0 0/30 * * * ?")
    public void refreshAppScheduler(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <String> entity = new HttpEntity<>(headers);

        restTemplate.exchange("https://nw-dashboard-service-app.herokuapp.com/dashboard/v1/refresh", HttpMethod.GET, entity, String.class).getBody();
    }
}
