package org.example.notification_sender;

public class LoggingNotificationSender implements NotificationSender {
    private final NotificationSender delegate;

    public LoggingNotificationSender(NotificationSender delegate) {
        this.delegate = delegate;
    }

    @Override
    public void send(String to, String message) {
        System.out.println("[Send Start] " + to + ": " + message);
        delegate.send(to, message);
        System.out.println("[Send Complete]");
    }
}
