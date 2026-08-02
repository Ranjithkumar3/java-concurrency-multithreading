package io.github.ranjith.concurrency;

public class VolatileStopDemo {
    public static void main(String[] args) throws InterruptedException {
        VolatileStopWorker volatileStopWorker = new VolatileStopWorker();
        Thread worker = new Thread(volatileStopWorker, "volatilestop-worker");
        worker.start();
        Thread.sleep(1200);
        volatileStopWorker.setRunningToFalse();
        worker.join();
        System.out.println("main observed the worker stop");
    }
}
