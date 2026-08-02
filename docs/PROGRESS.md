# Learning Progress

## Current status

- **Repository:** Java Concurrency Interview Lab
- **Java version:** 21
- **Build system:** Maven
- **Started:** 2026-08-01
- **Current exercise:** Exercise 2 — Thread coordination and task results
- **Overall status:** In progress
- **Last completed exercise:** Exercise 2 — `Callable`, `Future`, `join`, daemon threads, and thread states
- **Next action:** Begin Exercise 3 when ready

## Status definitions

- `Not started`: No implementation attempted
- `In progress`: Exercise started but not completely reviewed
- `Completed`: Code, explanation, notes, and interview questions reviewed
- `Skipped`: Intentionally postponed
- `Needs revision`: Previously completed but requires more practice

## Exercise checklist

| No. | Exercise | Status | Notes |
|---:|---|---|---|
| 1 | Creating threads with `Thread` and `Runnable` | Completed | Implemented, reviewed, demonstrated, and verified |
| 2 | `Callable`, `Future`, `join`, daemon threads, and thread states | Completed | Implemented, reviewed, demonstrated, and verified |
| 3 | Thread interruption and cooperative cancellation | Not started | |
| 4 | Reproducing a lost-update race condition | Not started | |
| 5 | Fixing shared state with `synchronized` | Not started | |
| 6 | Visibility with `volatile` | Not started | |
| 7 | Atomic variables and compare-and-set | Not started | |
| 8 | Java Memory Model and happens-before | Not started | |
| 9 | `ReentrantLock`, `tryLock`, and lock ordering | Not started | |
| 10 | `wait`, `notify`, and `notifyAll` | Not started | |
| 11 | Producer-consumer with `BlockingQueue` | Not started | |
| 12 | `ExecutorService`, `Callable`, and `Future` | Not started | |
| 13 | `ThreadPoolExecutor`, queues, and rejection policies | Not started | |
| 14 | Graceful executor shutdown | Not started | |
| 15 | `ConcurrentHashMap`, compound actions, and `LongAdder` | Not started | |
| 16 | `CountDownLatch`, `CyclicBarrier`, and `Semaphore` | Not started | |
| 17 | `CompletableFuture` composition and error handling | Not started | |
| 18 | Deadlock reproduction, diagnosis, and prevention | Not started | |
| 19 | Livelock, starvation, and thread-pool starvation | Not started | |
| 20 | Java 21 virtual threads | Not started | |
| 21 | Thread dumps and production troubleshooting | Not started | |
| 22 | Final revision and mock interview | Not started | |

## Current exercise details

### Exercise 1: Creating threads with `Thread` and `Runnable`

- **Status:** Completed
- **Started on:** 2026-08-01
- **Completed on:** 2026-08-01
- **Topic note:** `docs/01-multithreading-fundamentals.md`

### Learning objectives

- Understand the relationship between a process and a thread
- Create a thread by extending `Thread`
- Create a task by implementing `Runnable`
- Understand the difference between `start()` and `run()`
- Observe nondeterministic thread scheduling
- Name threads and inspect the currently executing thread

### Implementation

- **Classes created:** `ThreadCreationDemo`, `SubclassWorker`, `RunnableWorker`
- **Tests created:** None
- **Build result:** Passed (`mvn clean test`; no tests present yet)
- **Demonstration result:** Three runs confirmed nondeterministic interleaving; each worker preserved its own message order.

### My prediction

Yes. Because, the other two threads might be started using start() but only OS/JVM scheduler decides when they get their CPU time. So, the relative output order of all threads is not predictable. But, each thread preserves its own order of printing messages.

### My observations

The final message from `main` appeared before all worker output in two runs and between worker messages in one run. Each worker still printed messages 1, 2, and 3 in order.

### What I understood

`start()` begins `run()` on a new thread, while a direct `run()` call stays on the current thread. A `Runnable` describes a task and needs a `Thread` to execute it. `main` continues after starting workers unless it explicitly waits.

### Remaining doubts

- None recorded

### Interview questions

- What is the difference between a process and a thread?
- What is the difference between calling `start()` and calling `run()`?
- Why is implementing `Runnable` generally preferred over extending `Thread`?
- Can the execution order of two started threads be guaranteed?

### Key takeaway

Starting a thread does not guarantee when it will run relative to other threads; it only allows it to run independently.

### Exercise 2: `Callable`, `Future`, `join`, daemon threads, and thread states

- **Status:** Completed
- **Started on:** 2026-08-01
- **Completed on:** 2026-08-02
- **Topic note:** `docs/02-thread-coordination-and-results.md`

### Learning objectives

- Wait for a thread using `join()`
- Inspect basic thread states
- Return a value from work using `Callable` and retrieve it with `Future`
- Understand daemon-thread lifecycle behavior

### Implementation

- **Classes created:** `CountingWorker`, `JoinDemo`, `SumTask`, `CallableFutureDemo`, `HeartbeatDaemon`, `DaemonDemo`
- **Tests created:** None
- **Build result:** Passed (`mvn clean test`; no tests present yet)
- **Demonstration result:** `join()` kept `main` waiting until the worker terminated; `Future.get()` returned `15`; daemon runs exited after `main` without printing heartbeats.

### My prediction

Before `start()`, the worker will be in the `NEW` state. After `join()` returns, it will be `TERMINATED`. The final message from `main` cannot appear before any worker output because `join()` makes `main` wait for the worker to finish.

When `main` calls `Future.get()`, it waits until the task supplies its value; the sum task should return `15`.

A daemon thread may not print all ten heartbeats. Once `main` ends and no non-daemon threads remain, the JVM exits without waiting for daemon threads to finish.

### My observations

`CountingWorker` was `NEW` before start and `TERMINATED` after `join()`. `Future.get()` returned the calculated sum of `15`. In three daemon runs, only the final message from `main` printed because the JVM exited before the daemon was scheduled.

### What I understood

`join()` makes the calling thread wait for a target thread to finish. `Future.get()` also waits, but it returns the task's value. Daemon threads are background helpers and can stop abruptly when no non-daemon threads remain. `Thread.sleep()` pauses only the current thread.

### Remaining doubts

- None recorded

### Key takeaway

Choose `join()` for thread completion, `Future.get()` for a result, and daemon threads only for non-essential background work.

## Completed exercise log

Add one entry after each completed exercise.

### Interview sprint checkpoint — 2026-08-02

- **Practical demos completed:** Interruption and cooperative cancellation; lost-update race reproduction; `synchronized` and `AtomicInteger` counter fixes; `volatile` visibility flag; `ReentrantLock` with `tryLock()`; lock ordering; producer-consumer with `BlockingQueue`; `ExecutorService`; integrated `ThreadPoolExecutor`, `CompletableFuture`, and virtual-thread pipeline.
- **Interview concepts covered:** Thread lifecycle, `Runnable`/`Callable`, `Future`, `join()`, interruption, JMM visibility and happens-before, locks and deadlock prevention, concurrent collections, executor lifecycle, rejection policies, `CompletableFuture`, and virtual threads.
- **Mock MCQ result:** 29/30 correct.
- **Correction retained:** `LongAdder` is best for heavily contended metric updates; use `AtomicLong` when an exact current value is required.
- **Status:** Sprint coverage completed; full exercises remain available for later in-depth practice.

#### Exercise 2: `Callable`, `Future`, `join`, daemon threads, and thread states

- **Completed on:** 2026-08-02
- **Implemented classes:** `CountingWorker`, `JoinDemo`, `SumTask`, `CallableFutureDemo`, `HeartbeatDaemon`, `DaemonDemo`
- **Tests added:** None; this exercise used console demonstrations.
- **Topic note updated:** `docs/02-thread-coordination-and-results.md`
- **Build result:** `mvn clean test` passed on Java 21.
- **Concepts understood:** `join()`, basic thread states, `Callable`, `Future`, `FutureTask`, daemon lifecycle behavior, and `Thread.sleep()`.
- **Mistakes corrected:** `join()` makes the caller wait for the target; threads need not run simultaneously; daemon work is not guaranteed to finish.
- **Interview questions reviewed:** `join()` caller and target, `join()` versus `Future.get()`, and why daemons cannot perform essential work.
- **Remaining doubts:** None recorded.
- **Suggested commit message:** `Complete Exercise 2 coordination and task result demos`

#### Exercise 1: Creating threads with `Thread` and `Runnable`

- **Completed on:** 2026-08-01
- **Implemented classes:** `ThreadCreationDemo`, `SubclassWorker`, `RunnableWorker`
- **Tests added:** None; this exercise used a console demonstration.
- **Topic note updated:** `docs/01-multithreading-fundamentals.md`
- **Build result:** `mvn clean test` passed on Java 21.
- **Concepts understood:** Process versus thread, extending `Thread`, implementing `Runnable`, `start()` versus `run()`, thread naming, and nondeterministic scheduling.
- **Mistakes corrected:** Threads may execute concurrently, but they are not guaranteed to run simultaneously; output ordering across threads cannot be assumed.
- **Interview questions reviewed:** `start()` versus `run()`, why a `Runnable` needs a `Thread`, and evidence that `main` continues after `start()`.
- **Remaining doubts:** None recorded.
- **Suggested commit message:** `Complete Exercise 1 thread creation demo`

### Entry template

#### Exercise N: Exercise title

- **Completed on:** YYYY-MM-DD
- **Implemented classes:**
- **Tests added:**
- **Topic note updated:**
- **Build result:**
- **Concepts understood:**
- **Mistakes corrected:**
- **Interview questions reviewed:**
- **Remaining doubts:**
- **Suggested commit message:**

## Topics needing revision

| Topic | Reason | Review date |
|---|---|---|
| None | — | — |

## Interview readiness

| Area | Confidence | Evidence |
|---|---|---|
| Thread fundamentals | Not assessed | |
| Shared-state safety | Not assessed | |
| Java Memory Model | Not assessed | |
| Locks and coordination | Not assessed | |
| Executor framework | Not assessed | |
| Concurrent collections | Not assessed | |
| `CompletableFuture` | Not assessed | |
| Concurrency failure scenarios | Not assessed | |
| Virtual threads | Not assessed | |
| Production troubleshooting | Not assessed | |

Confidence values:

- Not assessed
- Low
- Developing
- Interview-ready
- Needs revision

## Next session instructions

1. Read `AGENTS.md`.
2. Review this progress file.
3. Inspect the current exercise implementation.
4. Continue the current incomplete exercise.
5. Do not skip directly to a complete solution.
6. Update this file after the exercise is reviewed.
