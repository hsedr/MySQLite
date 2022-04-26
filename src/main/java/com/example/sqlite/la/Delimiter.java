package com.example.sqlite.la;

import java.util.Locale;

/**
 * Token Class - Delimiter
 */
public enum Delimiter implements Type{
    /* Delimiter */
    ParentLeft("("),
    ParentRight(")"),
    Comma(","),
    Semicolon(";"),
    Quote("'");

    String regEx;
    String tag = "delimiter";

    Delimiter(String regEx){
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
