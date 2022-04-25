package com.example.sqlite.test;


import java.util.ArrayList;
import java.util.List;

public class DEATest {

    public static void main(String[] args){
        String str = "condition and condition;";
        String[] head;
        if(str.contains("and")) {
            head = str.split("(?<=and)|(?=and)");
            for (String h : head) System.out.println(h);
        }
        /*
        String[] splitted = str.split("(?=[\\W+])|(?<=[\\])");
        for (String string : splitted){
            if(!string.isBlank())
                System.out.println(string);
        }

         */
    }

    public static Object[] test(String word){
        if(word.charAt(word.length()-1) == ';'){
            word = word.substring(0, word.length()-1);
        }
        ArrayList<String> result = new ArrayList<>(List.of(word.split("\\s+", 2)));
        if(word.contains(""))
        result.add(";");
        return result.toArray();
    }
}
