package com.example.sqlite.la;

import java.util.Locale;

public enum Operator implements Type{
    /* Operator */
    LT("<"), // <
    LE("<="), // <=
    EQ("="), // =
    NE("=!"), // !=, <>
    GT(">"), // >
    GE(">="), // >=
    AND("and"),
    NOT("not"),
    OR("or")
    ;

    String regEx;
    String tag = "operator";

    Operator(String regEx){
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
