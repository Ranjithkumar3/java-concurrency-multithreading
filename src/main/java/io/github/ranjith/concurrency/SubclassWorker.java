package io.github.ranjith.concurrency;

public class SubclassWorker extends Thread {
    @Override
    public void run() {
        System.out.println("SubclassWorker message 1 from: " + Thread.currentThread().getName());
        System.out.println("SubclassWorker message 2 from: " + Thread.currentThread().getName());
        System.out.println("SubclassWorker message 3 from: " + Thread.currentThread().getName());
    }
}
