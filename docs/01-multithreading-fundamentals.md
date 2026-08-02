# Multithreading Fundamentals

## Quick reference

| Term | One-line definition |
|---|---|
| Process | The whole running Java program, including its memory and resources. |
| Thread | One independent path of execution inside a process. |
| `main` thread | The first thread that starts when a Java application begins. |
| `Thread` | Java's class for creating and managing a thread of execution. |
| `Runnable` | A description of work that a `Thread` can execute without returning a value. |
| `start()` | Begins a thread and causes its `run()` method to execute on that new thread. |
| `run()` | Defines a task's work; a direct call runs it on the current thread. |
| `join()` | Makes the calling thread wait until a target thread has finished. |
| Thread state | Java's label for a thread's current lifecycle stage. |
| `NEW` | Created but not yet started. |
| `RUNNABLE` | Running or ready to run when scheduled. |
| `TIMED_WAITING` | Waiting for a specified duration, such as during `sleep()`. |
| `TERMINATED` | Finished and unable to be restarted. |
| `Callable<T>` | A task that returns a value of type `T` and may throw an exception. |
| `Future<T>` | A handle for a result that a task will provide later. |
| `FutureTask<T>` | An object that runs a `Callable` and stores its future result. |
| `ExecutionException` | An exception indicating that a task failed while running in the background. |
| Daemon thread | A background thread that does not keep the JVM alive. |
| `Thread.sleep(...)` | Pauses the currently executing thread for approximately the requested time. |
| `InterruptedException` | A signal that a waiting or sleeping thread has been asked to stop waiting. |
| `interrupt()` | Requests that a target thread stop waiting or notice its interrupted status. |
| Interrupted status | A flag on a thread indicating that interruption has been requested. |
| Cooperative cancellation | A task stops itself after noticing an interruption or other cancellation request. |
| Shared mutable state | Data that multiple threads can read and change. |
| Race condition | A bug where a result depends on unpredictable thread timing. |
| Lost update | A race where one thread's write overwrites another thread's update. |
| `synchronized` | Ensures only one thread at a time enters code guarded by the same lock. |
| Intrinsic lock (monitor) | The built-in lock associated with every Java object and used by `synchronized`. |
| Visibility | Whether a write by one thread becomes observable by another thread. |
| `volatile` | Ensures visibility of a variable's latest write, but not atomic compound updates. |
| Atomic variable | A variable class that provides indivisible operations such as atomic increment. |
| `AtomicBoolean` | An atomic boolean value for operations such as one-time state changes. |
| `AtomicLong` | An atomic long value for large counters and numeric updates. |
| `AtomicReference<T>` | An atomic holder for an object reference, including a `String` reference. |
| Atomicity | The property that an operation happens as one indivisible unit. |
| Compare-and-set (CAS) | Atomically updates a value only when it still equals an expected value. |
| `ConcurrentHashMap` | A thread-safe map designed for concurrent access and updates. |
| `BlockingQueue` | A thread-safe queue that can wait when empty or full. |
| Producer-consumer | A pattern where producers add work and consumers remove and process it. |
| `LongAdder` | A high-throughput counter optimized for frequent concurrent updates. |
| Java Memory Model (JMM) | Java's rules for visibility, ordering, and safe communication between threads. |
| Happens-before | A guarantee that one action's effects are visible to another action. |
| `ExecutorService` | A service that runs submitted tasks and manages their worker threads. |
| Thread pool | A reusable group of worker threads used to execute many tasks. |
| `shutdown()` | Stops an executor from accepting new tasks while allowing submitted tasks to finish. |
| `shutdownNow()` | Requests that an executor stop active tasks and returns tasks that never started. |
| `ReentrantLock` | An explicit lock with features such as timed or non-blocking acquisition. |
| Reentrancy | A thread that already holds a lock can acquire that same lock again safely. |
| `tryLock()` | Attempts to acquire a lock without waiting forever. |
| Deadlock | Threads permanently wait for locks held by one another. |
| Lock ordering | A consistent global order for acquiring multiple locks to prevent deadlock. |

## Process and thread

A process is the whole running Java program and owns resources such as memory. A thread is one path of execution inside that process. The program begins with the `main` thread and can create worker threads.

## `Thread`

`Thread` is Java's class for an independent path of execution. Calling `start()` on a `Thread` lets Java run its `run()` method on that thread. A thread can be named to make logs and output easier to understand.

## `Runnable`

`Runnable` is a functional interface that describes work without being a thread itself. Its work is defined in `run()`. To execute a `Runnable` concurrently, give it to a `Thread` and call `start()` on that `Thread`. This separation usually makes code more flexible than extending `Thread`.

## Creating a thread

`SubclassWorker` extends `Thread`, so it is both a thread and the definition of its work. `RunnableWorker` implements `Runnable`, so it describes only the task; a separate `Thread` executes it. Implementing `Runnable` is generally more flexible because a class can extend only one class.

## `start()` compared with `run()`

`start()` asks Java to execute `run()` on a new thread. Calling `run()` directly executes it on the current thread, usually `main`, and does not create a new thread.

## Scheduling and output order

The output order between the three threads is not guaranteed. Across three runs, the final message from `main` appeared before all worker messages twice and between worker messages once. Each worker's own messages still appeared in its written order: 1, 2, then 3.

## Thread names

The names `subclass-worker` and `runnable-worker` identified which worker printed each line. The name `main` showed that the coordinator thread continued after starting both workers.

## `join()`

`targetThread.join()` makes the thread that calls it wait until `targetThread` has finished. It does not merge threads together. For example, when `main` calls `countingWorker.join()`, `main` waits and `countingWorker` continues until it terminates.

## Thread states

Thread state is Java's snapshot label for a thread's current stage. `NEW` means created but not started. `RUNNABLE` means able to run or currently running. `TIMED_WAITING` usually means waiting for a specified time, such as during `sleep()`. `TERMINATED` means finished and unable to be restarted.

## `Callable`

`Callable<T>` describes work that returns a value of type `T` and may fail with an exception. It is like `Runnable`, but `Runnable` does not return a result. Its work is defined in `call()` rather than `run()`.

## `Future`

A `Future<T>` represents the result that a task will provide later. Calling `get()` returns that result when it is ready; if the task is still running, the calling thread waits. Unlike `join()`, `get()` also provides the task's value. `get()` can report interruption or task failure through checked exceptions.

## `ExecutionException`

`ExecutionException` means that a background task failed while it was executing. For example, `Future.get()` can throw it to report an exception from a `Callable`.

## `FutureTask`

`FutureTask<T>` is an adapter that can run a `Callable<T>` and retain its result. It can be given to a `Thread` for execution and then queried through `get()` for the eventual value.

## Daemon threads

A daemon thread is a background helper thread. The JVM exits when no non-daemon threads remain, so it does not wait for daemon threads to finish. Daemon threads must not be used for work that must complete, such as saving important data.

## `Thread.sleep()`

`Thread.sleep(milliseconds)` pauses the currently executing thread for approximately the requested duration. It does not pause the whole program or any other threads. The duration is not exact, and the sleeping thread can be interrupted, so Java requires `InterruptedException` to be handled.

## `interrupt()` and interrupted status

Calling `targetThread.interrupt()` requests interruption of `targetThread`; it does not forcibly kill it. If the target is sleeping, waiting, or joining, Java normally causes that operation to throw `InterruptedException`, ending that wait. If the target is actively running, Java sets its interrupted-status flag instead; the task must check that flag with `isInterrupted()` and choose to stop.

## Cooperative cancellation

Java tasks normally cancel cooperatively: the task notices an interruption request and chooses to stop cleanly. A task that catches `InterruptedException` should usually restore its interrupted status and then finish or propagate the interruption. Forcibly stopping a thread is unsafe because it can leave shared state inconsistent.

## Shared mutable state

Shared mutable state is data that more than one thread can access and change, such as a shared counter. It is the source of many concurrency bugs because two threads can attempt related operations at the same time.

## Race condition and lost update

A race condition occurs when correctness depends on which thread happens to run first. `count++` is not one indivisible action: it reads the old value, calculates a new value, and writes it back. If two threads read the same old value before either writes, both can write the same new value. One increment is then lost; this is a lost-update race condition.

## `synchronized` and intrinsic locks

`synchronized` provides mutual exclusion: only one thread can execute code protected by the same lock at a time. Every Java object has a built-in intrinsic lock, also called a monitor. Synchronizing an instance method locks that method's object. When both increment tasks use the same `UnsafeCounter` object, synchronizing its increment operation prevents their read-modify-write steps from overlapping.

## Visibility and `volatile`

Visibility is whether one thread can reliably observe a value written by another thread. Declaring a field `volatile` makes reads observe the latest write to that field. It is useful for a simple shared flag, such as `running`, but it does not make `count++` safe because increment is a multi-step read-modify-write operation. In the stop-worker demo, `main` calls a method that writes `false` to the `running` field on a shared worker object; the worker thread reads that same field in its loop and reliably sees `false` because it is `volatile`. `private` controls direct source-code access, not whether threads can share an object through its methods.

## Atomicity and atomic variables

Atomicity means an operation is indivisible from other threads' point of view. Atomic classes such as `AtomicInteger` provide atomic operations including increment. They are appropriate for simple independent values such as counters, where using a lock would be unnecessary. Atomic operations are commonly built with compare-and-set (CAS): update a value only if it still equals the expected old value; otherwise retry using the newer value. In the two-thread counter demonstration, `AtomicInteger` produced the expected result of `200000` in all five runs without using `synchronized`. Other standard atomic classes include `AtomicBoolean`, `AtomicLong`, and `AtomicReference<T>`.

## `AtomicReference` and concurrent collections

There is no built-in `AtomicString` because `String` is immutable: changing a string means replacing its reference. `AtomicReference<String>` provides atomic replacement or compare-and-set for that reference when needed. A map has many keys and operations, so it is not represented by one atomic variable; use `ConcurrentHashMap` for a map accessed by multiple threads, together with its atomic compound operations such as `putIfAbsent()` or `compute()` when required.

## `BlockingQueue`, producer-consumer, and `LongAdder`

A `BlockingQueue` is a thread-safe queue for passing work between threads. A producer uses `put()` to add an item and waits if a bounded queue is full. A consumer uses `take()` to remove an item and waits if the queue is empty. This producer-consumer pattern avoids hand-written `wait()` and `notify()` coordination in most application code.

`LongAdder` is a counter designed for high contention. Instead of forcing every update through one shared value, it spreads updates across internal cells and combines them when `sum()` is called. It usually has higher update throughput than `AtomicLong`, but `sum()` is not an instantaneous exact snapshot during concurrent updates.

## Other atomic variable types

`AtomicBoolean` is useful for a shared state that must change only once, such as ensuring that shutdown begins only once. Its compare-and-set operation can atomically change `false` to `true` only for the first successful thread.

`AtomicLong` is the `long` counterpart of `AtomicInteger`. Use it for values outside the `int` range, such as large counters, byte totals, or identifiers.

`AtomicReference<T>` holds an object reference and supports atomic replacement or compare-and-set. It is useful when a thread must replace an entire immutable object or configuration snapshot only if the old reference has not changed.

## Java Memory Model and happens-before

The Java Memory Model defines how threads can see each other's writes and how operations may be ordered. A happens-before relationship guarantees that effects of one action are visible to another. Important examples are: actions before `Thread.start()` are visible to the started thread; a thread's completed actions are visible after another thread successfully returns from `join()`; unlocking a `synchronized` lock happens-before a later lock of that same lock; and a write to a `volatile` field happens-before a later read of that field.

## `ExecutorService` and thread pools

An `ExecutorService` manages worker threads and runs tasks submitted to it, so application code usually does not create a new `Thread` for every task. A fixed thread pool reuses a fixed number of workers. `submit()` accepts a task and returns a `Future` when a result or completion status is needed.

## Executor shutdown

`shutdown()` begins graceful shutdown: the executor rejects new tasks but lets already submitted tasks finish. `shutdownNow()` requests interruption of active tasks and returns queued tasks that did not start; it is not a guarantee that active tasks will stop. A program should always shut down an `ExecutorService` it creates.

## `ReentrantLock`, `tryLock()`, and deadlock

`ReentrantLock` is an explicit alternative to `synchronized`. Both are reentrant: a thread that already holds the lock can acquire it again. `ReentrantLock` must always be released in a `finally` block after successful acquisition. `tryLock()` attempts to acquire a lock and can return `false` instead of waiting forever, which is useful for timeouts and avoiding indefinite waits. It also supports timed acquisition, interruptible acquisition, fairness options, and multiple condition variables.

A deadlock occurs when threads permanently wait for one another's locks. A common pattern is thread A holding lock 1 while waiting for lock 2, and thread B holding lock 2 while waiting for lock 1. The primary prevention technique is lock ordering: every code path acquires the same set of locks in one consistent order.

## Interview summary

Threads let independent tasks make progress without making the main thread wait for each one to finish. A `Thread` executes work, while `Runnable` is a separate description of that work. Calling `start()` creates concurrent execution; calling `run()` directly does not.

## Remaining questions

None recorded.
