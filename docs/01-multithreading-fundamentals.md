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
| Atomicity | The property that an operation happens as one indivisible unit. |
| Compare-and-set (CAS) | Atomically updates a value only when it still equals an expected value. |
| Java Memory Model (JMM) | Java's rules for visibility, ordering, and safe communication between threads. |
| Happens-before | A guarantee that one action's effects are visible to another action. |
| `ExecutorService` | A service that runs submitted tasks and manages their worker threads. |
| Thread pool | A reusable group of worker threads used to execute many tasks. |
| `shutdown()` | Stops an executor from accepting new tasks while allowing submitted tasks to finish. |
| `shutdownNow()` | Requests that an executor stop active tasks and returns tasks that never started. |

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

Atomicity means an operation is indivisible from other threads' point of view. Atomic classes such as `AtomicInteger` provide atomic operations including increment. They are appropriate for simple independent values such as counters, where using a lock would be unnecessary. Atomic operations are commonly built with compare-and-set (CAS): update a value only if it still equals the expected old value; otherwise retry using the newer value.

## Java Memory Model and happens-before

The Java Memory Model defines how threads can see each other's writes and how operations may be ordered. A happens-before relationship guarantees that effects of one action are visible to another. Important examples are: actions before `Thread.start()` are visible to the started thread; a thread's completed actions are visible after another thread successfully returns from `join()`; unlocking a `synchronized` lock happens-before a later lock of that same lock; and a write to a `volatile` field happens-before a later read of that field.

## `ExecutorService` and thread pools

An `ExecutorService` manages worker threads and runs tasks submitted to it, so application code usually does not create a new `Thread` for every task. A fixed thread pool reuses a fixed number of workers. `submit()` accepts a task and returns a `Future` when a result or completion status is needed.

## Executor shutdown

`shutdown()` begins graceful shutdown: the executor rejects new tasks but lets already submitted tasks finish. `shutdownNow()` requests interruption of active tasks and returns queued tasks that did not start; it is not a guarantee that active tasks will stop. A program should always shut down an `ExecutorService` it creates.

## Interview summary

Threads let independent tasks make progress without making the main thread wait for each one to finish. A `Thread` executes work, while `Runnable` is a separate description of that work. Calling `start()` creates concurrent execution; calling `run()` directly does not.

## Remaining questions

None recorded.
