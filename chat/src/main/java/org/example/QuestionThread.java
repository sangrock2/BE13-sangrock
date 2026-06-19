package org.example;

public class QuestionThread extends Thread {
    private Chat chat;
    private String[] questions = {
            "Hi",
            "How are you?",
            "What are you doing?",
    };

    public QuestionThread(Chat chat) {
        this.chat = chat;
    }

    public void run() {
        for (String question : questions) {
            chat.question(question);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

}
