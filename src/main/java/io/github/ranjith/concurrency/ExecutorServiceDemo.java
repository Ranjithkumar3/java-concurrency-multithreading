package io.github.ranjith.concurrency;

import java.util.concurrent.*;

public class ExecutorServiceDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Integer> future = executor.submit(new SumTask());
        Integer result = future.get();

        System.out.println("Sum result: " + result);
        executor.shutdown();
    }
}
