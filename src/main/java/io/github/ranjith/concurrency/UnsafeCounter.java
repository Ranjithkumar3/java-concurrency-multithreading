package io.github.ranjith.concurrency;

public class UnsafeCounter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
