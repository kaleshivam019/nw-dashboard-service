package com.ey.nwdashboard.scheduler;

import com.ey.nwdashboard.entity.TrackerEntity;
import com.ey.nwdashboard.entity.UserEntity;
import com.ey.nwdashboard.model.MessageModelResponse;
import com.ey.nwdashboard.service.impl.TrackerDBServiceImpl;
import com.ey.nwdashboard.service.impl.UserDBServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class NWDashboardScheduler {

    public static Logger logger = Logger.getLogger("NWDashboardScheduler");

    @Autowired
    RestTemplate restTemplate;

    /**
     * This scheduler is used to refresh the application every 30 mins to overcome the problem from idle/shutdown state in heroku
     */
    /*@Scheduled(cron = "0 0/30 * * * ?")
    public void refreshAppScheduler(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <String> entity = new HttpEntity<>(headers);

        restTemplate.exchange("https://nw-dashboard-service-app.herokuapp.com/dashboard/v1/refresh", HttpMethod.GET, entity, String.class).getBody();
    }*/

    /**
     * This is the cron scheduler which will run on 1st day of every month at 12:02 AM
     */
    @Scheduled(cron = "0 2 0 1 1/1 *")
    public void pendingVacationTrackerScheduler(){
        logger.log(Level.INFO, "Setting vacation tracker entry to false - STARTED");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <String> entity = new HttpEntity<>(headers);

        ResponseEntity<MessageModelResponse> response = restTemplate.exchange("http://20.57.169.180:8080/dashboard/v1/defaulting-entry", HttpMethod.POST, entity, MessageModelResponse.class);

        if(response.getStatusCode().is2xxSuccessful()){
            logger.log(Level.INFO, "Setting vacation tracker entry to false - COMPLETED");
        }else if(response.getStatusCode().is5xxServerError()){
            logger.log(Level.SEVERE, "Exception while updating vacation tracker entry using scheduler");
        }
    }
}
