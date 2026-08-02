package io.github.ranjith.concurrency;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerDemo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(2);
        NumberProducer numberProducer = new NumberProducer(blockingQueue);
        NumberConsumer numberConsumer = new NumberConsumer(blockingQueue);

        Thread producer = new Thread(numberProducer, "producer");
        Thread consumer = new Thread(numberConsumer, "consumer");

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("Producer Consumer Demo done");
    }
}
