package com.nixsolutions.laba2;

import interfaces.task2.FractionNumber;
import interfaces.task2.FractionNumberOperation;

public class FractionNumberOperationImpl implements FractionNumberOperation {
    
    public FractionNumberOperationImpl(){}
    
    @Override
    public FractionNumber add(FractionNumber num1, FractionNumber num2) {
        int dividend1 = num1.getDividend();
        int divisior1 = num1.getDivisor();
        int dividend2 = num2.getDividend();
        int divisior2 = num2.getDivisor();

        int lcm = getLCM(divisior1, divisior2);

        int coff1 = lcm / divisior1;
        int coff2 = lcm / divisior2;

        dividend1 *= coff1;
        dividend2 *= coff2;

        int finalDivident = dividend1 + dividend2;
        int finalDivisor = lcm;

        return new FractionNumberImpl(finalDivident, finalDivisor);

    }


    @Override
    public FractionNumber sub(FractionNumber num1, FractionNumber num2) {
        int dividend1 = num1.getDividend();
        int divisior1 = num1.getDivisor();
        int dividend2 = num2.getDividend();
        int divisior2 = num2.getDivisor();

        int lcm = getLCM(divisior1, divisior2);

        int coff1 = lcm / divisior1;
        int coff2 = lcm / divisior2;

        dividend1 *= coff1;
        dividend2 *= coff2;

        int finalDivident = dividend1 - dividend2;
        int finalDivisor = lcm;
        return new FractionNumberImpl(finalDivident, finalDivisor);
    }
    @Override
    public FractionNumber div(FractionNumber num1, FractionNumber num2) {
        if (num2.value () == 0) {
            throw new ArithmeticException();
        }
        int dividend = num1.getDividend() * num2.getDivisor();
        int divisor = num1.getDivisor() * num2.getDividend();
        return new FractionNumberImpl(dividend, divisor);
    }

    @Override
    public FractionNumber mul(FractionNumber num1, FractionNumber num2) {
        int dividend = num1.getDividend() * num2.getDividend();
        int divisor = num1.getDivisor() * num2.getDivisor();
        return new FractionNumberImpl(dividend, divisor);
    }


    private int getGCD(int a, int b) {
        while (b > 0){
            int temp = b;
            b = a % b; 
            a = temp;
        }
        return a;
    }

    private  int getLCM(int a, int b){
        return a * (b / getGCD(a, b));
    }
}
