package org.example;

public class WorkerThread extends Thread {
    private SharedResource sharedResource;
    private String name;

    public WorkerThread(SharedResource sharedResource, String name) {
        this.sharedResource = sharedResource;
        this.name = name;
    }

    public void run() {
        while (true) {
            sharedResource.waitForResource(name);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

}
