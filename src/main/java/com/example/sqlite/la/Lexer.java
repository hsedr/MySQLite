package com.example.sqlite.la;

import java.util.ArrayList;
import java.util.List;

/**
 * Lexical Analyzer
 */
public class Lexer {

    final static int BEGINNING_STATE = 0;

    /**
     * Takes the Statement and returns a Token Stream.
     * Each character of the Statement is assigned to a
     * Token Class and added to the Stream.
     * @param word entered statement
     * @return list of Tokens
     */
    public List<Token> tokenize(String word){
        List<Token> tokenStream = new ArrayList<>();
        // position is saved for better error handling later on
        int state = BEGINNING_STATE;
        String[] characterStream = word.split("(?<=\\W)|(?=\\W+)");
        for(String character : characterStream){
            //spaces are ignored
            if(!character.equals(" ")) {
                tokenStream.add(matchToken(character, state));
                state++;
            }
        }
        return tokenStream;
    }

    /**
     * This Method takes a character and its position and returns a matching
     * Token. If no predefined Token Class is matched to the character, it is assumed
     * to be an identifier.
     * @param character current character
     * @param position current position in the statement
     * @return corresponding Token
     */
    private Token matchToken(String character, int position){
        for(Keyword keyword : Keyword.values()){
            if(keyword.matches(character)) return new Token(character, character, position);
        }
        for(Function function : Function.values()){
            if(function.matches(character)) return new Token(function.getTag(), character, position);
        }
        for(DataType dataType : DataType.values()){
            if(dataType.matches(character)) return new Token(dataType.getTag(), character, position);
        }
        for(Operator operator : Operator.values()){
            if(operator.matches(character)) return new Token(operator.getTag(), character, position);
        }
        for(Delimiter delimiter : Delimiter.values()){
            if(delimiter.matches(character)) return new Token(delimiter.getTag(), character, position);
        }
        return new Token("identifier", character, position);
    }
}
