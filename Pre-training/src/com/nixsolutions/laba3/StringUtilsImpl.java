package com.nixsolutions.laba3;


import interfaces.task3.StringUtils;

public class StringUtilsImpl implements StringUtils {

    public StringUtilsImpl() {}

    @Override
    public String compareWords(String str1, String str2) {
        StringBuilder  sb = new StringBuilder();
        char [] c = str1.toCharArray(); 
        char [] c1 = str2.toCharArray(); 

        for (int i = 0; i < c.length; i++) {
            if (!checkContains(c[i],c1)) {
                sb.append(c[i]);
            }             
        }
        return sb.toString();
    }

    private boolean  checkContains(char c, char [] c1) {
        for (int i = 0; i < c1.length; i++) {
            if (c == c1[i]) {
                return true ;
            }
        }
        return false;

    }

    @Override
    public String invert(String str) {
        if (str == null) {
            return "";
        }
        char [] c = str.toCharArray(); 
        int low = 0 ;
        int high = c.length-1;
        while (low < high ) {
            char tmp = c[low];
            c[low++] = c[high];
            c[high--] = tmp;
        }
        return new String(c);
    }

    @Override
    public double parseDouble(String str) throws IllegalArgumentException{
        if (str == null) {
            throw new NullPointerException();
        }
        String[] s = str.split(" ");
        String fracNum = s[0];
        try {
            return Double.parseDouble(fracNum);
        }catch (IllegalArgumentException ex) {
            IllegalArgumentException e = new IllegalArgumentException("incorrect parsing double value");
            ex.initCause(e);            
            throw(ex); 
        }


    }

    public static void main(String[] args) {
        StringUtilsImpl s = new StringUtilsImpl();
        System.out.println( s.invert("string | gnirts"));
        System.out.println( s.compareWords("stringTzero", "string"));
        try {
            System.out.println( s.parseDouble("1.23e.11"));
        }catch (IllegalArgumentException ex) {
            ex.printStackTrace(System.err);
            System.err.println("Message " + ex.getCause());
        }


    }

}
