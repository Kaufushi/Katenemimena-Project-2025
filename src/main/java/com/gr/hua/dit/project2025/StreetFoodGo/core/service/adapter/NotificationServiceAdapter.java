package com.gr.hua.dit.project2025.StreetFoodGo.core.service.adapter;

import com.gr.hua.dit.project2025.StreetFoodGo.core.service.model.NotificationResponseDto;
import com.gr.hua.dit.project2025.StreetFoodGo.core.service.port.NotificationServicePort;
import com.gr.hua.dit.project2025.StreetFoodGo.core.service.model.NotificationRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@Component
public class NotificationServiceAdapter implements NotificationServicePort {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public NotificationServiceAdapter(RestTemplate restTemplate,
                                      @Value("${notification.service.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @Override
    public boolean sendNotification(String recipient, String message) {
        try {
            NotificationRequestDto request = new NotificationRequestDto(recipient, message);
            ResponseEntity<NotificationResponseDto> response = restTemplate.postForEntity(
                    baseUrl,
                    request,
                    NotificationResponseDto.class
            );

            return response.getBody() != null && response.getBody().isSent();

        } catch (Exception e) {
            // Log the error
            System.err.println("Failed to send notification: " + e.getMessage());
            return false;
        }
    }
}