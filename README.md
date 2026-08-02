# Java Concurrency & Multithreading Lab

A hands-on Java 21 lab for learning how concurrent programs behave, why they fail, and how to make them safe. The focus is practical understanding for real backend work and technical interviews—not memorising APIs in isolation.

## Why concurrency matters

A normal program starts with one thread: `main`. Concurrency lets it start independent work without forcing every task to happen strictly one after another.

```text
main thread
├── accepts or coordinates work
├── starts worker tasks
└── waits only when it needs a result or completion
```

This is useful for handling many requests, performing blocking I/O, processing background work, and keeping applications responsive. It also introduces risks: scheduling is unpredictable, shared state can race, and blocked threads can deadlock.

## What this lab teaches

The exercises build from thread basics to production-style concurrency:

- Creating and naming threads with `Thread` and `Runnable`
- Returning results with `Callable`, `Future`, and `CompletableFuture`
- Waiting, cancellation, interruption, daemon lifecycle, and thread states
- Race conditions, lost updates, visibility, atomicity, and the Java Memory Model
- `synchronized`, `volatile`, atomic variables, `ReentrantLock`, and lock ordering
- Producer-consumer coordination with `BlockingQueue`
- Executors, thread pools, rejection policies, and graceful shutdown
- Concurrent collections and high-contention counters
- Deadlocks, virtual threads, diagnostics, and interview practice

## Key terms at a glance

| Term | Meaning |
|---|---|
| Process | The whole running Java program and its resources. |
| Thread | One independent path of execution inside a process. |
| `start()` | Starts work on a new thread. |
| `run()` | Defines work; calling it directly stays on the current thread. |
| `join()` | Makes the calling thread wait for another thread to finish. |
| `interrupt()` | Requests cooperative cancellation; it does not forcibly kill a thread. |
| `synchronized` | Allows one thread at a time into code guarded by the same lock. |
| `volatile` | Guarantees visibility of a variable’s latest write, not atomic compound updates. |
| `AtomicInteger` | Provides atomic numeric updates such as increment without an explicit lock. |
| Happens-before | A visibility and ordering guarantee defined by the Java Memory Model. |
| `BlockingQueue` | A thread-safe handoff queue that waits when empty or full. |
| `ExecutorService` | Manages worker threads and runs submitted tasks. |
| `CompletableFuture` | Builds asynchronous result pipelines with composition and error handling. |
| Virtual thread | A lightweight Java 21 thread suited to many blocking I/O tasks. |

For the complete cheat sheet and detailed explanations, see [Multithreading Fundamentals](docs/01-multithreading-fundamentals.md).

## Core distinctions worth remembering

### Visibility is not atomicity

`volatile` is ideal for a simple shared flag such as `running`: one thread writes `false`, another reliably sees it and exits. It does **not** make `count++` safe, because increment is a read-modify-write sequence.

Use `synchronized` or an atomic variable for compound shared-state updates.

### Waiting is not cancellation

- `sleep()` pauses the current thread for a period.
- `join()` waits for a target thread to finish.
- `Future.get()` waits for a task result.
- `interrupt()` asks a target thread to stop waiting or stop work cooperatively.

### Platform threads and virtual threads

Platform threads map to operating-system threads. Virtual threads are lightweight JVM-managed threads that make waiting cheaper: when a virtual thread blocks on I/O, the JVM can reuse its carrier platform thread for another virtual task.

Virtual threads improve scalability for many blocking tasks; they do not make CPU-bound work faster or remove the need for thread safety.

## Learning workflow

Each exercise follows this loop:

1. Learn the smallest useful concept.
2. Predict the behavior before running code.
3. Write the Java implementation and tests yourself.
4. Run, observe, and explain the result.
5. Review the design, thread safety, and interview answer.

The learner owns all Java source and test code. This repository uses the tutor only for exercises, review, verification, and documentation.

## Environment and build

- JDK 21
- Maven
- JUnit 5
- Base package: `io.github.ranjith.concurrency`

Run the verification build:

```shell
mvn clean test
```

## Documentation

- [Learning progress and exercise checklist](docs/PROGRESS.md)
- [Multithreading fundamentals: cheat sheet and detailed definitions](docs/01-multithreading-fundamentals.md)
- [Thread coordination and task results notes](docs/02-thread-coordination-and-results.md)

## Suggested study order

Start with thread fundamentals, then shared-state safety, coordination, executors, concurrent collections, asynchronous composition, and finally virtual threads and production troubleshooting. The full ordered exercise roadmap is maintained in [the progress tracker](docs/PROGRESS.md).
