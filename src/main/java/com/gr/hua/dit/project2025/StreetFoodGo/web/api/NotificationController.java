package com.gr.hua.dit.project2025.StreetFoodGo.web.api;

import com.gr.hua.dit.project2025.StreetFoodGo.core.service.model.NotificationRequestDto;
import com.gr.hua.dit.project2025.StreetFoodGo.core.service.model.NotificationResponseDto;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

    @PostMapping("/email")
    public NotificationResponseDto sendEmail(@RequestBody NotificationRequestDto request) {

        log.info("Receive notifcation request for recipiest: {}", request.getRecipient());
        log.info("Message: {}", request.getMessage());

        boolean sent = true;
        String error = null;

        log.info("Notifcation sent successfully to {}", request.getRecipient());

        return new NotificationResponseDto(sent, error);
    }
}


