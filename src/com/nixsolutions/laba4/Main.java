package com.nixsolutions.laba4;

import java.util.Iterator;
import java.util.Random;

import com.nixsolutions.laba2.FractionNumberImpl;
import com.nixsolutions.laba2.FractionNumberOperationImpl;
import com.nixsolutions.laba5.ArrayCollectionImpl;

import interfaces.task2.FractionNumber;
import interfaces.task2.FractionNumberOperation;
import interfaces.task5.ArrayCollection;

public class Main {
    public Main () {}

    public static void main(String[] args) {
        ArrayCollection<FractionNumber> collection = new ArrayCollectionImpl<>();
        for (int i = 0; i < 5; i++) {
            collection.add(getRandomFractionNumber());
        }
        
        System.out.println(collection.size());
        FractionNumber sum = new FractionNumberImpl();
        
        sum.setDividend(0);
        sum.setDivisor(1);
        
        FractionNumberOperation oper = new FractionNumberOperationImpl();        
        Iterator<FractionNumber> iter = collection.iterator();

        while (iter.hasNext()) {
            sum = oper.add(sum, iter.next());
        }
        System.out.println(sum.toStringValue());
    }

    private static FractionNumber getRandomFractionNumber() {
        FractionNumber fn = new FractionNumberImpl();
        fn.setDividend(new Random().ints(1, (10 + 1)).findFirst().getAsInt());
        fn.setDivisor(new Random().ints(1, (10 + 1)).findFirst().getAsInt());
        return fn;
    }


}
