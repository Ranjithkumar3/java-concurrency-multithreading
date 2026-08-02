package io.github.ranjith.concurrency;

public class InterruptionDemo {
    public static void main(String[] args) throws InterruptedException {
        InterruptibleWorker worker = new InterruptibleWorker();
        worker.setName("interruptible-worker");
        worker.start();
        Thread.sleep(1000);
        worker.interrupt();
        worker.join();
        System.out.println("Worker stopped; main continues: " + Thread.currentThread().getName());
    }
}
