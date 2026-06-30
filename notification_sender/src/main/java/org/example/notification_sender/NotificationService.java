package org.example.notification_sender;

public class NotificationService {
    private final NotificationSender sender;

    public NotificationService(NotificationSender sender) {
        this.sender = sender;
    }

    public void notifyUser(String to, String message) {
        sender.send(to, message);
    }


}
