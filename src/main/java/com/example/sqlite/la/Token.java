package com.example.sqlite.la;

public class Token {

    private String type;
    private String value;
    private int position;

    Token(String t, String v, int position){
        this.type = t;
        this.value = v;
        this.position = position;
    }

    @Override
    public String toString(){
        return "<type:" + type + "><value:" + value + "><position:" + position +">";
    }
}