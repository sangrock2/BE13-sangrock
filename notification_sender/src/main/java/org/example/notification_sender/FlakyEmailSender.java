package org.example.notification_sender;

public class FlakyEmailSender implements NotificationSender {
    private int attempt = 0;
    @Override
    public void send(String to, String message) {
        attempt++;

        if (attempt < 3) {
            throw new RuntimeException("Network Error (Retry " + attempt + ")");
        }

        System.out.printf("[EMAIL] (try %d Success) to=%s : %s%n", attempt, to, message);
    }
}