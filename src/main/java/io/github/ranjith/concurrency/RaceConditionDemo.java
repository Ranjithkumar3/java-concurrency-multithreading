package io.github.ranjith.concurrency;

public class RaceConditionDemo {
    public static void main(String[] args) throws InterruptedException {
        UnsafeCounter counter = new UnsafeCounter();
        IncrementTask taskOne = new IncrementTask(counter);
        IncrementTask taskTwo = new IncrementTask(counter);

        Thread threadOne = new Thread(taskOne, "increment-one");
        Thread threadTwo = new Thread(taskTwo, "increment-two");

        threadOne.start();
        threadTwo.start();

        threadOne.join();
        threadTwo.join();

        System.out.println("Expected: 200000");
        System.out.println("Actual: " + counter.getCount());
    }
}
