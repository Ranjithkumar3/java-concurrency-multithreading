package io.github.ranjith.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SumTask sumTask = new SumTask();
        FutureTask<Integer> futureTask = new FutureTask<>(sumTask);

        Thread sumThread = new Thread(futureTask);
        sumThread.setName("sum-worker");
        sumThread.start();
        Integer result = futureTask.get();
        System.out.println("Sum result: " + result);
    }
}
