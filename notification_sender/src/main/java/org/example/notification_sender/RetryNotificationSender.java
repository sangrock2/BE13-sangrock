package org.example.notification_sender;

public class RetryNotificationSender implements NotificationSender {
    private final NotificationSender delegate;

    public RetryNotificationSender(NotificationSender delegate) {
        this.delegate = delegate;
    }

    @Override
    public void send(String to, String message) {
        int max_retries = 3;

        for (int i = 0; i < max_retries; i++) {
            try {
                delegate.send(to, message);
                return;
            } catch (Exception e) {
                System.out.println("Retry attempt #" + i);
            }
        }

        throw new RuntimeException("Retry attempt #" + max_retries);
    }
}
