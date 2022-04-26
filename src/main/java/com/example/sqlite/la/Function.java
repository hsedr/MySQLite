package com.example.sqlite.la;

import java.util.Locale;

/**
 * Token Class - Function
 */
public enum Function implements Type {
    /* SQL Functions */
    Avg("avg"),
    Count("count"),
    Max("max"),
    Min("min"),
    Sum("sum");

    String regEx;
    String tag = "function";

    Function(String regEx){
        this.regEx = regEx;
    }

    @Override
    public boolean matches(String character){
        return character.toLowerCase(Locale.GERMANY).equals(this.regEx);
    }

    @Override
    public String getTag(){
        return tag;
    }
}
