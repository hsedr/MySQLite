package com.example.sqlite.parser;

import com.example.sqlite.utils.Table;
import com.example.sqlite.utils.Token;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.stream.Collectors;

/**
 * Provides key functionalities that are needed
 * in order to generate a Parser.
 */
public class ParserGenerator {

    private static HashMap<String, ArrayList<ArrayList<Token>>> rules;
    private static String grammar = "";

    /**
     * Loads a Grammar definition from a file and calls
     * all needed methods in order to generate a parsing table.
     *
     * @param path path to that file
     * @throws IOException input exception
     */
    public void loadGrammar(String path) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(path));
        String tokenList = in.readLine();
        generateTokenList(tokenList);
        String line = "";
        while (line != null) {
            grammar += line;
            line = in.readLine();
        }
        grammar = grammar.replaceAll("\n", "").replace("\t", "").trim();
        Tokenizer lexer = new Tokenizer(Grammar.grammarTokenList);
        lexer.tokenize(grammar);
        //System.out.println(lexer.getTokenStream());
        Driver driver = new Driver(Tokenizer.getInstance(), Grammar.grammarParseTable);
        driver.parse();
        //driver.traverseAST();
        SyntaxTreeVisitor visitor = new SyntaxTreeVisitor();
        driver.accept(visitor);
        //System.out.println(visitor.rules);
        ParserGenerator gen = new ParserGenerator();
        gen.generateParseTable(Grammar.parseTable, visitor.rules);
    }

    /**
     * This method generates a Parsing Table with the help of First and Follow
     * Sets.
     * The Algorithm works as following ->
     * for each NonTerminal A:
     *  for each derivation β of A:
     *      compute the First Set : A -> β, β =>* aβ, "a" is element of First(β), "a" is a Terminal;
     *      for each computed Terminal an in the First Set, unless it is EPSILON:
     *          insert rule: Table[A, a] = β;
     *          if the First Set contains EPSILON, we compute Follow(A):
     *              for each Token b of this Set, unless it is EPSILON:
     *                  insert rule: Table[A, b] = ε;
     *
     * @param table table to be initialized
     * @param rules rules of a specific grammar
     */
    public void generateParseTable(Table table, HashMap<String, ArrayList<ArrayList<Token>>> rules) {
        ParserGenerator.rules = rules;
        for (String alpha : rules.keySet()) {
            for (ArrayList<Token> rule : rules.get(alpha)) {
                ArrayList<Token> strFirstSet = firstSet(rule);
                String prettyPrint = rule.stream().map(Token::value).collect(Collectors.joining(" "));
                for (Token token : strFirstSet) {
                    if (!token.isEpsilon()) {
                        System.out.println("Table[" + alpha + ", " + token.value() + "] = " + prettyPrint);
                        table.insertValue(alpha, token.value(), prettyPrint);
                    }
                }
                if (strFirstSet.contains(Grammar.epsilonRule())) {
                    ArrayList<Token> strFollowSet = followSet(alpha);
                    if(rule.size() == 1 && rule.get(0).isNonTerminal())
                        strFollowSet.addAll(followSet(rule.get(0).value()));
                    for (Token token : strFollowSet) {
                        if (!token.isEpsilon()) {
                            System.out.println("Table[" + alpha + ", " + token.value() + "] = " + "ε");
                            table.insertValue(alpha, token.value(), "ε");
                        }
                    }
                }
            }
        }
    }

    /**
     * This method computes the First Set of a NonTerminal A.
     *
     * @param alpha   NonTerminal
     * @return First Set of a NonTerminal
     */
    public ArrayList<Token> firstSet(String alpha) {
        ArrayList<ArrayList<Token>> rules = ParserGenerator.rules.get(alpha);
        ArrayList<Token> firsts = new ArrayList<>();
        // A -> β
        for (ArrayList<Token> rule : rules) {
            Token token = rule.get(0);
            // A => Aβ - direct left recursion -> grammar is not LL(1)
            if (token.value().equals(alpha)) {
                System.out.println("Left Recursion found. Grammar is not LL(1)");
                return new ArrayList<>();
            }
            // β =>* aβ, a = Terminal
            if (token.isTerminal() || token.isEpsilon()) {
                firsts.add(token);
            } else {
                // β =>* Xβ, X = NonTerminal -> compute First(X)
                firsts.addAll(firstSet(token.value()));}
        }
        return (ArrayList<Token>) firsts.stream().distinct().collect(Collectors.toList());
    }

    /**
     * This method computes the First Set of a specific derivation.
     * It looks at the first Token, and decides depending on whether it's
     * a Terminal or NonTerminal, what to do next.
     *
     * @param rule    specific derivation
     * @return List of First elements
     */
    public ArrayList<Token> firstSet(ArrayList<Token> rule) {
        ArrayList<Token> firsts = new ArrayList<>();
        Token first = rule.get(0);
        // A -> β =>* aβ, a = Terminal
        if (first.isTerminal()) {
            firsts.add(first);
        }
        else if (first.isNonTerminal()) {
            // A -> β =>* Xβ, X = NonTerminal
            firsts.addAll(firstSet(first.value()));
        } else {
            firsts.add(new Token("EPSILON", "EPSILON", 0));
        }
        return (ArrayList<Token>) firsts.stream().distinct().collect(Collectors.toList());
    }

    /**
     * This method computes the Follow Set of a NonTerminal X.
     * It is assumed that X =>* EPSILON.
     * <p>
     * The Algorithm works as following ->
     * for each NonTerminal A definition
     *      for each Derivation of A, that contains X:
     *          compute index of X;
     *          if index = derivation.size() , means if X is at last position:
     *              Token "$" is inserted into follows;
     *              if A != X: Follow(A) is inserted into follows;
     *          else:
     *              if follow Y of X is a NonTerminal:
     *                  First(Y) is added to follow;
     *                  if First(Y) contains EPSILON:
     *                    compute Follows(A) and add it to follows;
     *              else:
     *                  Token is added to follow;
     *
     * @param alpha   NonTerminal
     * @return set of Follows
     */
    public ArrayList<Token> followSet(String alpha) {
        Token tokenAlpha = new Token("NonTerminal", alpha, 0);
        ArrayList<Token> follows = new ArrayList<>();
        for (String str : ParserGenerator.rules.keySet()) {
            for (ArrayList<Token> rule : ParserGenerator.rules.get(str)) {
                if (rule.contains(tokenAlpha)) {
                    int index = rule.indexOf(tokenAlpha);
                    if (index + 1 == rule.size()) {
                        follows.add(new Token("$", "$", 0));
                        if (!alpha.equals(str)) follows.addAll(followSet(str));
                        break;
                    } else {
                        Token follow = rule.get(index + 1);
                        if (follow.isNonTerminal()) {
                            ArrayList<Token> followFirstSet = firstSet(follow.value());
                            follows.addAll(followFirstSet);
                            if (followFirstSet.contains(Grammar.epsilonRule())) {
                                follows.addAll(followSet(str));
                            }
                        } else {
                            follows.add(follow);
                        }
                    }
                }
            }
        }
        return (ArrayList<Token>) follows.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Generates a Token List from a line at the top of the grammar file.
     * <p>
     * //TODO: more robust algorithm for generating token lists defined by an user
     *
     * @param input
     */
    public void generateTokenList(String input) {
        int openBr = input.indexOf("{");
        int closeBr = input.indexOf("}");
        String tokenList = input.substring(openBr + 1, closeBr);
        ArrayList<String> keyWords = new ArrayList<>();
        String[] tokens = tokenList.split(",");
        for (String token : tokens) {
            String[] pair = token.split(":(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            String key = pair[0].trim();
            String value = pair[1].trim().replaceAll("\"", "");
            keyWords.add(key);
            Grammar.tokenList.put(key, value);
        }
        String regex = "(" + String.join("|", keyWords) + ")";
        Grammar.grammarTokenList.put("Terminal", regex);
        Grammar.grammarTokenList.put("NonTerminal", "[a-zA-Z0-9]+");
        //System.out.println("Grammar Token List: " + Grammar.grammarTokenList);
        //System.out.println("Token List: " + Grammar.tokenList);
    }
}
