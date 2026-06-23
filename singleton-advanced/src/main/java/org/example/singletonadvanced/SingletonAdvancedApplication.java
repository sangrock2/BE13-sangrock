package org.example.singletonadvanced;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SingletonAdvancedApplication {
    static int badMismatch = 0;
    static int goodMismatch = 0;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n========== 1. Bad vs Good  ==========");
        int N = 100;

        Thread[] threads1 = new Thread[N];

        for (int i = 0; i < N; i++) {
            String expectName = "User" + i;

            threads1[i] = new Thread(() -> {
                String actualName = GreetingServiceBad.getInstance().greet(expectName);

                if (!actualName.trim().equals(expectName)) {
                    synchronized (SingletonAdvancedApplication.class) {
                        badMismatch++;
                    }
                }
            });
        }

        for (Thread thread : threads1) thread.start();
        for (Thread thread : threads1) thread.join();

        Thread[] threads2 = new Thread[N];

        for (int i = 0; i < N; i++) {
            String expectName = "User" + i;
            threads2[i] = new Thread(() -> {
                String actualName = GreetingServiceGood.getInstance().greet(expectName);

                if (!actualName.trim().equals(expectName)) {
                    synchronized (SingletonAdvancedApplication.class) {
                        goodMismatch++;
                    }
                }
            });
        }

        for (Thread thread : threads2) thread.start();
        for (Thread thread : threads2) thread.join();

        System.out.println("Bad : " + badMismatch);
        System.out.println("Good : " + goodMismatch);
    }
}
