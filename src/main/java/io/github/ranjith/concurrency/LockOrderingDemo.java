package io.github.ranjith.concurrency;

import java.util.concurrent.locks.ReentrantLock;

public class LockOrderingDemo {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lockA = new ReentrantLock();
        ReentrantLock lockB = new ReentrantLock();

        OrderedLockTask task1 = new OrderedLockTask(lockA, lockB, "task-1");
        OrderedLockTask task2 = new OrderedLockTask(lockA, lockB, "task-2");

        Thread thread1 = new Thread(task1, "thread-1");
        Thread thread2 = new Thread(task2, "thread-2");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("LockOrdering demo done");
    }
}
