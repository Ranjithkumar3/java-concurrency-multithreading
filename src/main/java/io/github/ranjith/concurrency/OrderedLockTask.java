package io.github.ranjith.concurrency;

import java.util.concurrent.locks.ReentrantLock;

public class OrderedLockTask implements Runnable {
    private final ReentrantLock firstLock;
    private final ReentrantLock secondLock;
    private final String taskName;

    OrderedLockTask(ReentrantLock firstLock, ReentrantLock secondLock, String taskName) {
        this.firstLock = firstLock;
        this.secondLock = secondLock;
        this.taskName = taskName;
    }

    @Override
    public void run() {
        firstLock.lock();
        try {
            System.out.println("Task - " + taskName + " acquired by first lock");
            Thread.sleep(200);
            secondLock.lock();
            try {
                System.out.println("Task - " + taskName + " acquired both the locks");
            } finally {
                secondLock.unlock();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        } finally {
            firstLock.unlock();
        }
    }
}
