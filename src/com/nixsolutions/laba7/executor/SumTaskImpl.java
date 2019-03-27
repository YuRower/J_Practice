package com.nixsolutions.laba7.executor;

import java.math.BigInteger;
import java.util.Random;

import interfaces.task7.executor.SumTask;

public class SumTaskImpl implements SumTask {

    public SumTaskImpl() {
    }

    private int count;
    private long max;
    private BigInteger result = new BigInteger("0");

    @Override
    public boolean execute() throws Exception {
        for (int i = 0; i < count; i++) {
            result = result.add(getRandom());
        }
        return true;
    }

    @Override
    public int getTryCount() {
        return count;
    }

    @Override
    public void incTryCount() {
        count++;
    }

    @Override
    public BigInteger getRandom() {
        Random rand = new Random();
        double randomValue = max * rand.nextDouble();
        return BigInteger.valueOf((long) randomValue);
    }

    @Override
    public BigInteger getResult() {
        return result;
    }

    @Override
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void setMax(long max) {
        if (max < 1) {
            throw new IllegalArgumentException();
        }
        this.max = max;
    }
}
