package com.example.sqlite.parser;

import com.example.sqlite.utils.Token;

import java.util.*;

public class Tokenizer {

    static public List<Token> tokenStream = new ArrayList<>();
    public static HashMap<String, String> tokenList = new HashMap<>();
    static Tokenizer instance = null;
    static String command;

    Tokenizer(HashMap<String, String> tokenList){
        Tokenizer.tokenList = tokenList;
    }

    public Tokenizer(){
        instance = null;
    }

    /**
     * Returns instance of the ZRTokenizer if instance's not null.
     * Otherwise, creates a new instance.
     *
     * @return ZRTokenizer instance.
     */
    public static Tokenizer getInstance() {
        if (instance == null) {
            instance = new Tokenizer();
        }
        return instance;
    }

    public List<Token> getTokenStream(){
        return tokenStream;
    }

    /**
     * Initializes the Tokenizer.
     * Takes a list of Tokens from a specific grammar.
     *
     * @param tokenList list of Tokens
     */
    public void initLexer(HashMap<String, String> tokenList) {
        this.tokenList = tokenList;
    }

    /**
     * When called this method returns the first
     * element of the Token Stream and deletes it from the
     * list.
     *
     * @return Token
     */
    public Token nextToken() {
        Token token = tokenStream.get(0);
        tokenStream.remove(0);
        return token;
    }

    /**
     * Takes the Statement and returns a Token Stream.
     * Each character of the Statement is assigned to a
     * Token Class and added to the Stream.
     *
     * @param word entered statement
     * @return list of Tokens
     */
    public void tokenize(String word) {
        this.command = word;
        List<Token> tokenStream = new ArrayList<>();
        // position is saved for better error handling later on
        int state = 0;
        ArrayList<String> characterStream = new ArrayList<>(List.of(word.split("(?=\\W)|(?<=\\W)(?=\\w)")));
        characterStream.removeIf(item -> item == null || item.equals(" ") || item.isBlank());
        for (String character : characterStream) {
            tokenStream.add(matchToken(character.trim(), state));
            state++;
        }
        tokenStream.add(new Token("$", "$", tokenStream.size()));
        this.tokenStream.addAll(tokenStream);
    }

    /**
     * This Method takes a character and its position and returns a matching
     * Token. If no predefined Token Class is matched to the character, it is matched
     * with the "unexpected" Token Class.
     *
     * @param character current character
     * @param position  current position in the statement
     * @return corresponding Token
     */
    private Token matchToken(String character, int position) {
        Iterator<Map.Entry<String, String>> entries = tokenList.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            if (character.matches(entry.getValue())) return new Token(entry.getKey(), character, position);
        }
        return new Token("unexpected", character, position);
    }
}

