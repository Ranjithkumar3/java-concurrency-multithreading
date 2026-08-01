# Multithreading Fundamentals

## Process and thread

A process is the whole running Java program and owns resources such as memory. A thread is one path of execution inside that process. The program begins with the `main` thread and can create worker threads.

## Creating a thread

`SubclassWorker` extends `Thread`, so it is both a thread and the definition of its work. `RunnableWorker` implements `Runnable`, so it describes only the task; a separate `Thread` executes it. Implementing `Runnable` is generally more flexible because a class can extend only one class.

## `start()` compared with `run()`

`start()` asks Java to execute `run()` on a new thread. Calling `run()` directly executes it on the current thread, usually `main`, and does not create a new thread.

## Scheduling and output order

The output order between the three threads is not guaranteed. Across three runs, the final message from `main` appeared before all worker messages twice and between worker messages once. Each worker's own messages still appeared in its written order: 1, 2, then 3.

## Thread names

The names `subclass-worker` and `runnable-worker` identified which worker printed each line. The name `main` showed that the coordinator thread continued after starting both workers.

## Interview summary

Threads let independent tasks make progress without making the main thread wait for each one to finish. A `Thread` executes work, while `Runnable` is a separate description of that work. Calling `start()` creates concurrent execution; calling `run()` directly does not.

## Remaining questions

None recorded.
