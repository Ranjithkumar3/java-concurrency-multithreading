package io.github.ranjith.concurrency;

public class DaemonDemo {
    public static void main(String[] args) {
        HeartbeatDaemon heartbeatDaemon = new HeartbeatDaemon();
        heartbeatDaemon.setName("heartbeat-daemon");
        heartbeatDaemon.setDaemon(true);
        heartbeatDaemon.start();
        System.out.println("Main is finished: " + Thread.currentThread().getName());
    }
}
