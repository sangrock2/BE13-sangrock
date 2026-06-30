package org.example.notification_sender;

public class KakaoNotificationSender implements NotificationSender {
    @Override
    public void send(String to, String message) {
        System.out.printf("[KAKAO] to=%s : %s%n", to, message);
    }
}
