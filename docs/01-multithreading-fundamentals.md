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

## Interview summary

Threads let independent tasks make progress without making the main thread wait for each one to finish. A `Thread` executes work, while `Runnable` is a separate description of that work. Calling `start()` creates concurrent execution; calling `run()` directly does not.

## Remaining questions

None recorded.
