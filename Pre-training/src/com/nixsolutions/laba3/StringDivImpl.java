package com.nixsolutions.laba3;

import interfaces.task3.StringDiv;

public class StringDivImpl implements StringDiv {
    
    public StringDivImpl () {}
    
    @Override
    public double div(String str1, String str2) {
        if (str1 == null || str2 == null) {
            throw new NullPointerException("str1 " + str1 + "or str2 " + str2 + " contains null"  );
        }
        double dividend , divisor ;
        try {
            dividend = Double.parseDouble(str1);
            divisor = Double.parseDouble(str2);
        }catch (IllegalArgumentException  ex ) {
            IllegalArgumentException e = new IllegalArgumentException("incorrect parsing double value");
            ex.initCause(e); 
            
            throw(ex);
            }
        if (divisor == 0 ) {
            throw new ArithmeticException("Division by zero");
        }

        return dividend / divisor;
    }
    public static void main(String[] args) {
        StringDivImpl s = new StringDivImpl ();
        System.out.println(s.div("15", "31"));
    }

}
