package io.github.ranjith.concurrency;

import java.util.concurrent.BlockingQueue;

public class NumberProducer implements Runnable {
    private final BlockingQueue<Integer> queue;

    NumberProducer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for(int i=1; i<=5; i++) {
            try {
                queue.put(i);
                System.out.println("Produced: " + i + " from: " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
