package com.example.sqlite.la;

import java.util.Locale;

/**
 * Token Class - Data Type
 */
public enum DataType implements Type{
    /* SQL Data Type */
    Char("[a-zA-Z]", "char"),                   // 'a'
    Double("[1-9][0-9]*\\.[0-9]*", "double"),   // 3.0
    Float("[1-9][0-9]*.[0-9]*", "float"),       // 3.0
    Int("[1-9][0-9]*", "int"),                  // 3
    Varchar("[a-zA-Z0-9]*", "varchar");         // 'varchar'

    String regEx;
    String tag = "constant";

    DataType(String regEx, String tag){
        this.regEx = regEx;
        this.tag = tag;
    }

    @Override
    public boolean matches(String character){
        return character.toLowerCase(Locale.GERMANY).matches(this.regEx);
    }

    @Override
    public String getTag(){
        return tag;
    }
}
