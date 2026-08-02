package io.github.ranjith.concurrency;

import java.util.concurrent.*;

public class ConcurrencyPipelineDemo {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.CallerRunsPolicy());
        CompletableFuture<String> order1 = processOrder("order-1",executor);
        CompletableFuture<String> order2 = processOrder("order-2",executor);
        CompletableFuture<String> order3 = processOrder("order-3",executor);
        CompletableFuture<String> order4 = processOrder("order-4",executor);

        CompletableFuture<Void> allOrders = CompletableFuture.allOf(order1, order2, order3, order4);
        allOrders.join();

        System.out.println(order1.join());
        System.out.println(order2.join());
        System.out.println(order3.join());
        System.out.println(order4.join());

        Thread notificationThread = Thread.startVirtualThread(() -> {
            System.out.println("Virtual notification started from: " + Thread.currentThread().getName());
            try {
                Thread.sleep(200);
                System.out.println("Virtual notification completed from: " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        });

        notificationThread.join();
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Concurrency pipeline demo completed");
    }

    private static CompletableFuture<String> processOrder(String orderLabel, Executor executor) {
        return CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("Starting " + orderLabel + " from: " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(300);
                        if("order-3".equals(orderLabel)) {
                            throw new IllegalStateException();
                        } else {
                            return orderLabel;
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    }
                }
        , executor).thenApply((label) -> {
            System.out.println("Processing " + label + " from: " + Thread.currentThread().getName());
            return "Processed " + label;
        }).exceptionally((failure) -> {
            System.out.println("Order failed: " + orderLabel);
            return "Fallback for " + orderLabel;
        });
    }
}

/* Output:

Starting order-4 from: main
Starting order-3 from: pool-1-thread-2
Starting order-1 from: pool-1-thread-1
Processing order-4 from: main
Order failed: order-3
Processing order-1 from: pool-1-thread-1
Starting order-2 from: pool-1-thread-2
Processing order-2 from: pool-1-thread-2
Processed order-1
Processed order-2
Fallback for order-3
Processed order-4
Virtual notification started from:
Virtual notification completed from:
Concurrency pipeline demo completed

Explanation:
Your output is exactly what the pool configuration predicts. The order is nondeterministic, but this run can be explained like this.

Your executor has:

core threads = 1
max threads = 2
queue capacity = 1
CallerRunsPolicy

  1. order-1 is submitted.

Starting order-1 from: pool-1-thread-1

The pool creates its first core worker.

  2. order-2 is submitted.

pool-1-thread-1 is busy
  → order-2 enters the one-slot queue

It does not start yet.

  3. order-3 is submitted.

queue is full
  → pool creates its second worker
  → order-3 starts on pool-1-thread-2

        4. order-4 is submitted.

worker 1 busy
worker 2 busy
queue full
  → CallerRunsPolicy activates
  → main thread runs order-4 itself

That explains:

Starting order-4 from: main
Processing order-4 from: main

main is slowed down for about 300 ms while it runs that task. This is backpressure.

  5. After the sleep:

order-3 throws its intentional IllegalStateException
  → thenApply is skipped
  → exceptionally runs
  → Fallback for order-3

order-1 succeeds
  → thenApply runs
  → Processed order-1

        6. Once worker 2 finishes order-3, it takes the queued order-2:

Starting order-2 from: pool-1-thread-2
Processing order-2 from: pool-1-thread-2

        7. allOrders.join() waits until all four pipelines complete. Then your code prints results in its written order:

Processed order-1
Processed order-2
Fallback for order-3
Processed order-4

        8. The virtual thread starts:

Virtual notification started from:

The name is blank because Thread.startVirtualThread(...) creates an unnamed virtual thread by default. It sleeps, the JVM can free its carrier thread while it waits, then it
resumes:

Virtual notification completed from:

        9. main waits for that virtual thread with join(), shuts down the executor, waits for executor termination, then prints:

Concurrency pipeline demo completed

The key evidence is order-4 from: main: it proves CallerRunsPolicy ran the saturated task on the submitting thread instead of rejecting it.
*/