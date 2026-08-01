package io.github.ranjith.concurrency;

public class JoinDemo {
    public static void main(String[] args) throws InterruptedException {
        CountingWorker countingWorker = new CountingWorker();
        countingWorker.setName("counting-worker");

        System.out.println("Before start: " + countingWorker.getState());
        countingWorker.start();
        countingWorker.join();

        System.out.println("After join: " + countingWorker.getState());
        System.out.println("Main continues after the worker finishes: " + Thread.currentThread().getName());
    }
}
