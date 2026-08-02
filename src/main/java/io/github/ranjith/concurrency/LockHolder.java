package io.github.ranjith.concurrency;

import java.util.concurrent.locks.ReentrantLock;

public class LockHolder implements Runnable {
    private final ReentrantLock reentrantLock;

    public LockHolder(ReentrantLock reentrantLock) {
        this.reentrantLock = reentrantLock;
    }

    @Override
    public void run() {
        reentrantLock.lock();
        try {
            System.out.println("Holder: " + Thread.currentThread().getName() + " acquired the lock");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            reentrantLock.unlock();
            System.out.println("Holder: " + Thread.currentThread().getName() + " released the lock");
        }
    }
}
