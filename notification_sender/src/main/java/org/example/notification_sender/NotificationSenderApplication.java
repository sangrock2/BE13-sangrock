package org.example.notification_sender;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationSenderApplication {
    public static void main(String[] args) {
        String user = "kim";
        String message = "Hello World!";

        NotificationService emailService = new NotificationService(new EmailNotificationSender());
        emailService.notifyUser(user, message);

        NotificationService smsService = new NotificationService(new SmsNotificationSender());
        smsService.notifyUser(user, message);

        NotificationService kakaoService = new NotificationService(new KakaoNotificationSender());
        kakaoService.notifyUser(user, message);

        System.out.println("\n====================");

        NotificationSender sender =
                new TimingNotificationSender(       // ③ 가장 바깥: 전체 소요 시간 측정
                        new LoggingNotificationSender(  // ② 로그 남기고
                                new RetryNotificationSender(// ① 실패하면 재시도하며
                                        new FlakyEmailSender())));// (실제 발송 대상)

        new NotificationService(sender).notifyUser(user, message);

        System.out.println("\n====================");

        NotificationSender sender2 =
                new TimingNotificationSender(       // ③ 가장 바깥: 전체 소요 시간 측정
                        new RetryNotificationSender(// ① 실패하면 재시도하며
                                new LoggingNotificationSender(  // ② 로그 남기고
                                        new FlakyEmailSender())));// (실제 발송 대상)

        new NotificationService(sender2).notifyUser(user, message);




    }

}
