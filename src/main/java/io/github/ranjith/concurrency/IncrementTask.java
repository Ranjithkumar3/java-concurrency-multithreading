package io.github.ranjith.concurrency;

public class IncrementTask implements Runnable {
    private final UnsafeCounter counter;

    public IncrementTask(UnsafeCounter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        int i=1;

        while(i<=100000) {
            counter.increment();
            i++;
        }
    }
}
