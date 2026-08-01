package io.github.ranjith.concurrency;

public class ThreadCreationDemo {
    public static void main(String[] args) {
        System.out.println("Starting from: " + Thread.currentThread().getName());
        SubclassWorker subclassWorker = new SubclassWorker();
        subclassWorker.setName("subclass-worker");

        RunnableWorker runnableWorker = new RunnableWorker();
        Thread runnableThread = new Thread(runnableWorker);
        runnableThread.setName("runnable-worker");

        subclassWorker.start();
        runnableThread.start();

        System.out.println("Main has started both workers from: " + Thread.currentThread().getName());
    }
}
