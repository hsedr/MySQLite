package com.example.sqlite;

import com.example.sqlite.parser.*;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        ParserGenerator gen = new ParserGenerator();
        gen.loadGrammar("src/main/resources/arithmeticExp.txt");
        while (true) {
            printPrompt();
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine().trim();
            String head = input.split("")[0];
            if (head.equals(".")) {
                switch (doMetaCommand(input)) {
                    case (0):
                        continue;
                    case (-1):
                        System.out.printf("Unrecognized command '%s' %n", input);
                        continue;
                }
            }
            switch (prepareStatement(input)) {
                case (0):
                    break;
                case (-1):
                    System.out.printf("Unrecognized key word at start of '%s' %n", input);
            }
        }
    }

    /**
     * Prints prompt to console.
     */
    private static void printPrompt() {
        System.out.print("db > ");
    }

    /**
     * Wrapper for meta commands.
     *
     * @param command entered meta command
     * @return 0 if command exists, else -1
     */
    private static int doMetaCommand(String command) {
        if (command.equals(".exit")) {
            System.exit(0);
            return 0;
        }
        return -1;
    }

    /**
     * Wrapper for statement types.
     *
     * @param input entered SQL statement
     * @return 0 if statement type exists, else -1
     */
    private static int prepareStatement(String input) {
        Tokenizer lexer = new Tokenizer();
        lexer.initLexer(Grammar.tokenList);
        lexer.tokenize(input);
        Driver driver = new Driver(Tokenizer.getInstance(), Grammar.parseTable);
        driver.parse();
        driver.traverseAST();
        return 0;
    }
}
