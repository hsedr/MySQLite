package com.example.sqlite.utils;

/**
 * Public record for Tokens.
 */
public record Token(String tokenClass, String value, int position) {

    public boolean isTerminal(){
        return this.tokenClass.equals("Terminal");
    }

    public boolean isNonTerminal(){
        return this.tokenClass.equals("NonTerminal");
    }

    public boolean isEpsilon(){
        return this.tokenClass.equals("EPSILON");
    }

    public boolean isEOF(){
        return this.tokenClass.equals("$");
    }
}
