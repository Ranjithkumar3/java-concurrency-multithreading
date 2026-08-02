package io.github.ranjith.concurrency;

public class VolatileStopWorker implements Runnable {
    private volatile boolean running = true;

    @Override
    public void run() {
        while (running) {
            System.out.println("Running from: " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public void setRunningToFalse() {
        this.running = false;
    }
}
