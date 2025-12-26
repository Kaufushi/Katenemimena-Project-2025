package com.gr.hua.dit.project2025.StreetFoodGo.core.service.port;
/**
 * Port interface for sending notifications to users
 * This defines the contract for the external notification service.
 * The core services will depend on this interface, not the implementation.
 */
public interface NotificationServicePort {
    /**
     * Sends a notification message to a user (customer or shop owner)
     *
     * @param recipientEmail the email of the recipient
     * @param message the notification message
     * @return true if the notification was sent successfully, false otherwise
     */
    boolean sendNotification(String recipientEmail, String message);
}
