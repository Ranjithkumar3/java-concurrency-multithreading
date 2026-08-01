package io.github.ranjith.concurrency;

public class HeartbeatDaemon extends Thread {
    @Override
    public void run() {
        for(int i=1; i<=10; i++) {
            System.out.println("Heartbeat " + i + " from: " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
