package io.github.ranjith.concurrency;

import java.util.concurrent.Callable;

public class SumTask implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        Integer sum = 0;

        for(int i=1; i<=5; i++) {
            sum += i;
        }

        return sum;
    }
}
