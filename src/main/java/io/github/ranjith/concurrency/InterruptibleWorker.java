package io.github.ranjith.concurrency;

public class InterruptibleWorker extends Thread {
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            System.out.println("Working from: " + Thread.currentThread().getName());
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
