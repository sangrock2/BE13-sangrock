package org.example;

public class SharedResource extends Thread {
    private boolean isAvailable = false;

    public synchronized void waitForResource(String resourceName) {
        while (!isAvailable) {
            try {
                System.out.println(resourceName + " is waiting for resource...");
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(resourceName + " got the resource!");
            isAvailable = false;
        }
    }

    public synchronized void makeResourceAvailable() {
        isAvailable = true;
        System.out.println("Resource is now available!");
        notifyAll();
    }
}
