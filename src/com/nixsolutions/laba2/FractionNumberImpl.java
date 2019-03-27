package com.nixsolutions.laba2;

import interfaces.task2.FractionNumber;

public class FractionNumberImpl implements FractionNumber{
    private int dividend; 
    private int divisor; 

    public FractionNumberImpl() {}
    public FractionNumberImpl(int dividend, int divisor) {
        if (divisor == 0 ) {
            throw new IllegalArgumentException("Division by zero");
        }
        this.dividend = dividend;
        this.divisor = divisor;
    }

    @Override
    public int getDividend() {
        return dividend;
    }

    @Override
    public int getDivisor() {
        return divisor;
    }

    @Override
    public void setDividend(int dividend) {
        this.dividend=dividend;
    }

    @Override
    public void setDivisor(int divisor) {
        if (divisor == 0 ) {
            throw new IllegalArgumentException("Division by zero");
        }
        this.divisor=divisor;

    }

    @Override
    public String toStringValue() {
        return dividend + "/"+  divisor;
    }

    @Override
    public double value() {
        return (dividend / divisor);
    }

}
