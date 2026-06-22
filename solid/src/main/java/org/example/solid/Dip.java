package org.example.solid;

// 의존 관계 역전 원칙

import java.util.ArrayList;
import java.util.List;

public class Dip {
    public interface MessageSender {
        void send(String message);
    }

    public class EmailSender implements MessageSender {
        @Override
        public void send(String message) {
            System.out.println("Sending email: " + message);
        }
    }

    public class SmsSender implements MessageSender {
        @Override
        public void send(String message) {
            System.out.println("Sending sms: " + message);
        }
    }

    public class MockSender implements MessageSender {
        private List<String> messages = new ArrayList<>();

        @Override
        public void send(String message) {
            messages.add(message);
        }

        public List<String> getMessages() {
            return messages;
        }
    }

    public class NotificationService {
        private MessageSender sender;

        public NotificationService(MessageSender sender) {
            this.sender = sender;
        }

        public void notify(String message) {
            sender.send(message);
        }
    }

}
