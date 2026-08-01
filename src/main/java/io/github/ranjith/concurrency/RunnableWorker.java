package io.github.ranjith.concurrency;

public class RunnableWorker implements Runnable {
    @Override
    public void run() {
        System.out.println("RunnableWorker message 1 from: " + Thread.currentThread().getName());
        System.out.println("RunnableWorker message 2 from: " + Thread.currentThread().getName());
        System.out.println("RunnableWorker message 3 from: " + Thread.currentThread().getName());
    }
}
