package io.github.ranjith.concurrency;

public class CountingWorker extends Thread {
    @Override
    public void run() {
        System.out.println("Count 1 from: " + Thread.currentThread().getName());
        System.out.println("Count 2 from: " + Thread.currentThread().getName());
        System.out.println("Count 3 from: " + Thread.currentThread().getName());
        System.out.println("Count 4 from: " + Thread.currentThread().getName());
        System.out.println("Count 5 from: " + Thread.currentThread().getName());
    }
}
