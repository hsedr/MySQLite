package com.example.sqlite.test;

import com.example.sqlite.la.Lexer;
import com.example.sqlite.la.Token;

import java.util.Locale;

public class LexerTest {
    public static void main(String[] args) {
        Lexer lexer = new Lexer();
        String stmt = "select all from table where name = 'Max';";
        for(Token token : lexer.tokenize(stmt.toUpperCase(Locale.ROOT))) {
            System.out.println(token.toString());
        }
    }
}
