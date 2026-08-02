package io.github.ranjith.concurrency;

public class AtomicCounterDemo {
    public static void main(String[] args) throws InterruptedException {
        AtomicCounter counter = new AtomicCounter();

        AtomicIncrementTask task1 = new AtomicIncrementTask(counter);
        AtomicIncrementTask task2 = new AtomicIncrementTask(counter);

        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Expected: 200000");
        System.out.println("Actual: " + counter.getCount());
    }
}
