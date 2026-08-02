package io.github.ranjith.concurrency;

public class AtomicIncrementTask implements Runnable {
    private final AtomicCounter counter;

    AtomicIncrementTask(AtomicCounter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        int i = 1;

        while(i <= 100000) {
            counter.increment();
            i++;
        }
    }
}
