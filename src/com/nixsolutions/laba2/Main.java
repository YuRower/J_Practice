package com.nixsolutions.laba2;

public class Main {
    public Main() {
    }
    public static void main(String[] args) {
        FractionNumberOperationImpl operations = new FractionNumberOperationImpl();
        FractionNumberImpl num1 = new FractionNumberImpl(1, 4);
        FractionNumberImpl num2 = new FractionNumberImpl(0, 1);
        System.out.println(operations.add(num1, num2).toStringValue());
        System.out.println(operations.sub(num1, num2).toStringValue());
        System.out.println(operations.div(num1, num2).toStringValue());
        System.out.println(operations.mul(num1, num2).toStringValue());
    }


}
