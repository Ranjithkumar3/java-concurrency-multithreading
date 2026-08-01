# Learning Progress

## Current status

- **Repository:** Java Concurrency Interview Lab
- **Java version:** 21
- **Build system:** Maven
- **Started:** 2026-08-01
- **Current exercise:** Exercise 1 — Creating threads
- **Overall status:** In progress
- **Last completed exercise:** Exercise 1 — Creating threads with `Thread` and `Runnable`
- **Next action:** Begin Exercise 2 when ready

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
| 2 | `Callable`, `Future`, `join`, daemon threads, and thread states | Not started | |
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

## Completed exercise log

Add one entry after each completed exercise.

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
