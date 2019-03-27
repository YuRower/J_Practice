package com.nixsolutions.laba4;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import interfaces.task4.MapUtils;

public class MapUtilsImpl implements MapUtils{
    
    public MapUtilsImpl () {}
    
    @Override
    public Map<String, Integer> findThrees(String str) {
        if (str == null ) {
            throw new NullPointerException();
        }     

        HashMap<String,Integer> dictionary = new HashMap<>();
        String patternString = "[a-zA-Z0-9]{3}";
        
        for (int i = 0; i < str.length()-2; i++) {
            String word = str.substring(i, i+3);
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(word);

            if ( matcher.matches()) {
                if (dictionary.containsKey(word)) {
                    int count = dictionary.get(word);
                    dictionary.put(word, ++count);
                }else {
                    dictionary.put(word, 1);

                }
            }
        }
        return dictionary;
    }
    
    public static void main(String[] args) {
        MapUtils map = new MapUtilsImpl();
        Map<String, Integer> storage = map.findThrees("1234 345_23 1234 abc cabc");
        for (Map.Entry<String, Integer> map1  : storage.entrySet()) {
            System.out.println(map1.getKey() + " = " +  map1.getValue());
        }

    }
}
