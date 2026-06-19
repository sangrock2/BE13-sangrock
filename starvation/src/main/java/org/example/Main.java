package org.example;

public class Main {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        new WorkerThread(resource, "worker1").start();
        new WorkerThread(resource, "worker2").start();
        new WorkerThread(resource, "worker3").start();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    resource.makeResourceAvailable();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}