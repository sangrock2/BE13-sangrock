 package org.example;

public class Main {
    public static void main(String[] args) {
        Chat chat = new Chat();

        QuestionThread questionThread = new QuestionThread(chat);
        AnswerThread answerThread = new AnswerThread(chat);

        questionThread.start();
        answerThread.start();
    }
}