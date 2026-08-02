package io.github.ranjith.concurrency;

import java.util.concurrent.locks.ReentrantLock;

public class TryLockWorker implements Runnable {
    private final ReentrantLock reentrantLock;

    TryLockWorker(ReentrantLock reentrantLock) {
        this.reentrantLock = reentrantLock;
    }

    @Override
    public void run() {
        if(reentrantLock.tryLock()) {
            try {
                System.out.println("Trylock worker: " + Thread.currentThread().getName() + " acquired the lock");
            } finally {
                reentrantLock.unlock();
            }
        } else {
            System.out.println("Trylock worker: " + Thread.currentThread().getName() + " could not acquire the lock");
        }
    }
}
