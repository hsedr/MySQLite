package com.example.sqlite.parser;

import com.example.sqlite.utils.Node;
import com.example.sqlite.utils.Token;
import com.example.sqlite.utils.Visitor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Fetches the rules from the AST created by the Parser/Driver
 * and puts it into a HashMap representation.
 */
public class SyntaxTreeVisitor implements Visitor {

    // HashMap with the derivations for each Key/NonTerminal
    public HashMap<String, ArrayList<ArrayList<Token>>> rules = new HashMap<>();
    // currently, defined NonTerminal
    String currentAlpha = "";
    // List of every derivation for this particular NonTerminal
    ArrayList<ArrayList<Token>> currentDerivations = new ArrayList<>();
    // current derivation being generated from the AST
    ArrayList<Token> currenDerivation = new ArrayList<>();

    /**
     * The grammar-grammar is designed in such a way that
     * NonTerminals and Terminals, as well as new definitions of NTs and
     * each derivation for a NT can be easily taken from the tree.
     *
     * @param node currently active Node
     */
    @Override
    public void visitNode(Node node) {
        for(Node child : node.getChildren()) {
            switch (child.getData()) {
                // ";" indicates the end of a NonTerminal definition
                case "SCOL":
                    currentDerivations.add(new ArrayList<>(currenDerivation));
                    rules.put(currentAlpha, new ArrayList<>(currentDerivations));
                    currentDerivations = new ArrayList<>();
                    currenDerivation = new ArrayList<>();
                    currentAlpha = "";
                    break;
                // indicates a new definition of a NonTerminal
                case "Alpha":
                    // Alpha => NonTerminal => actual value
                    currentAlpha = child.getChildren().get(0).getChildren().get(0).getData();
                    break;
                // deeper in the definition we encounter a Terminal | NonTerminal | EPSILON
                case "DontCare":
                    // DontCare => NonTerminal | Terminal | EPSILON => actual value
                    String dontCare = child.getChildren().get(0).getData();
                    String value = child.getChildren().get(0).getChildren().get(0).getData();
                    currenDerivation.add(new Token(dontCare, value, 0));
                    break;
                // indicates start of another derivation for same NonTerminal
                case "PIPE":
                    currentDerivations.add(new ArrayList<>(currenDerivation));
                    currenDerivation = new ArrayList<>();
            }
            child.accept(this);
        }
    }
}
