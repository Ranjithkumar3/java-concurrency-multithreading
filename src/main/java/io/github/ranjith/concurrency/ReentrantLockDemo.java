package io.github.ranjith.concurrency;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock sharedLock = new ReentrantLock();

        Thread lockHolder = new Thread(new LockHolder(sharedLock), "lock-holder");
        Thread tryLockWorker = new Thread(new TryLockWorker(sharedLock), "try-lock-worker");

        lockHolder.start();
        Thread.sleep(200);

        tryLockWorker.start();
        lockHolder.join();
        tryLockWorker.join();

        System.out.println("Lock demo finished from: " + Thread.currentThread().getName());
    }
}
