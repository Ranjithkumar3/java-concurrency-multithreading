package io.github.ranjith.concurrency;

import java.util.concurrent.BlockingQueue;

public class NumberConsumer implements Runnable {
    private final BlockingQueue<Integer> queue;

    NumberConsumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for(int i=1; i<=5; i++) {
            try {
                int n = queue.take();
                System.out.println("Consumed: " + n + " from: " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
