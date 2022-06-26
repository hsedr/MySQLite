package com.example.sqlite.parser;

import com.example.sqlite.utils.Table;
import com.example.sqlite.utils.Token;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * This class holds the Token List for Grammar definitions and the Parsing Table
 * for Grammar definitions, as well as the placeholders tokenList and parseTable that
 * are filled when a Grammar is successfully parsed.
 */
public class Grammar {

    public static Token epsilonRule(){
        return new Token("EPSILON", "EPSILON", 0);
    }

    public static HashMap<String, String> tokenList = new HashMap<>();

    public static Table parseTable = new Table();

    public static LinkedHashMap<String, String> grammarTokenList = new LinkedHashMap<>() {{
        put("SCOL", ";");
        put(":", ":");
        put("PIPE", "\\|");
        put("EPSILON", "EPSILON");
    }};

    public static Table grammarParseTable = new Table(){{
        insertValue("Start", "NonTerminal", "Grammar");
        insertValue("Grammar", "NonTerminal", "Rule Rules");
        insertValue("Rule", "NonTerminal", "Alpha : Production SCOL");
        insertValue("Rules", "NonTerminal", "Rule Rules");
        insertValue("Rules", "$", "ε");
        insertValue("Production", "NonTerminal", "Beta Productions");
        insertValue("Production", "Terminal", "Beta Productions");
        insertValue("Production", "EPSILON", "Beta Productions");
        insertValue("Productions", "PIPE", "PIPE Production");
        insertValue("Productions", "SCOL", "ε");
        insertValue("Beta", "NonTerminal", "DontCare Betas");
        insertValue("Beta", "Terminal", "DontCare Betas");
        insertValue("Beta", "EPSILON", "DontCare Betas");
        insertValue("Betas", "NonTerminal", "Beta");
        insertValue("Betas", "Terminal", "Beta");
        insertValue("Betas", "EPSILON", "Beta");
        insertValue("Betas", "PIPE", "ε");
        insertValue("Betas", "SCOL", "ε");
        insertValue("DontCare", "Terminal", "Terminal");
        insertValue("DontCare", "NonTerminal", "NonTerminal");
        insertValue("DontCare", "EPSILON", "EPSILON");
        insertValue("Alpha", "NonTerminal", "NonTerminal");
    }};
}
