package org.example.notification_sender;

public class TimingNotificationSender implements NotificationSender {
    private final NotificationSender delegate;

    public TimingNotificationSender(NotificationSender delegate) {
        this.delegate = delegate;
    }

    @Override
    public void send(String to, String message) {
        long startTime = System.currentTimeMillis();

        try {
            delegate.send(to, message);
        } finally {
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            System.out.println("Elapsed time: " + elapsedTime + " ms");
        }
    }
}
