package org.example;

public class AnswerThread extends Thread {
    private Chat chat;
    private String[] answers = {
            "Hello",
            "I'm fine, thank you!",
            "I'm coding in Java"
    };

    public AnswerThread(Chat chat) {
        this.chat = chat;
    }

    public void run() {
        for (String answer : answers) {
            chat.answer(answer);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

}
