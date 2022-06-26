package com.example.sqlite.parser;

import com.example.sqlite.utils.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * Driver for LL(1)-Languages with given parse table.
 */
public class Driver implements VisitableNode {

    private Tokenizer lexer;
    private Table parseTable;
    private final Node syntaxTree = new Node("Start");
    // "Start" is expected to be the start symbol of every grammar
    // "$" indicates that the stack is empty
    private final Stack<Node> stack = new Stack(){{push(new Node("$")); push(syntaxTree);}};
    // top of stack
    private String tos = stack.peek().getData();

    public Driver(Tokenizer lexer, Table parseTable){
        this.lexer = lexer;
        this.parseTable = parseTable;
    }

    /**
     * Initializes the Tokenizer.
     *
     * @param lexer Tokenizer
     */
    public void setLexer(Tokenizer lexer){
        this.lexer = lexer;
    }

    /**
     * Initializes the parsing table the Driver shall run on.
     *
     * @param parseTable parse table of a Grammar
     */
    public void setParseTable(Table parseTable){
        this.parseTable = parseTable;
    }

    /**
     * This method tries to find the leftmost derivation of the
     * entered String. A String is accepted if the Driver exits with an empty
     * stack.
     * The Driver runs on a parsing table of a LL(1)-Language.
     * Note:
     * Because the leftmost derivation is generated on the stack,
     * the components of each rule are pushed as Nodes. This means the AST is constructed with the
     * help of the stack.
     * Each element of the derivation of a NonTerminal can be interpreted as its child.
     *
     */
    public void parse(){
        if(lexer == null || parseTable == null){
            System.out.println("Driver is not properly initialized!");
            return;
        }
        Token lookAhead = lexer.nextToken();
        // base case: tos == lookAhead == $ -> stack empty
        while(!(tos.equals(lookAhead.tokenClass()) && lookAhead.isEOF())) {
            // tos == lookAhead != $ -> tos can be deleted, next lookAhead is fetched -> we move on
            if (tos.equals(lookAhead.tokenClass()) && !lookAhead.isEOF()) {
                stack.pop().addChild(new Node(lookAhead.value()));
                tos = stack.peek().getData();
                lookAhead = lexer.nextToken();
            // tos is a Non-Terminal -> Table[tos, lookAhead] gives the reduction
            // the String is syntactically incorrect if no reduction is found
            } else {
                ArrayList<String> value = parseTable.getValue(tos, lookAhead.tokenClass());
                // if no reduction is found
                if (value.isEmpty()) {
                    //TODO: more explicit error message
                    System.out.println("Incorrect");
                    System.out.println("expected: '" + tos + "', given: '" + lookAhead.value() + "," + lookAhead.tokenClass() + "' at position: " + lookAhead.position());
                    return;
                // if reduction is found
                } else {
                    // reference of active Node is saved
                    Node current = stack.pop();
                    // if value is EPSILON we just pop the element of the stack
                    if (!value.contains("ε")) {
                        Collections.reverse(value);
                        // value is pushed unto the stack
                        for (String str : value) {
                            Node child = new Node(str);
                            current.addChild(0, child);
                            // child is added to active node
                            stack.push(child);
                        }
                    }
                    // after reduction -> new top of stack
                    tos = stack.peek().getData();
                }
            }
        }
        System.out.println("Correct.");
    }

    /**
     * Prints a syntax tree on the Console.
     *
     * @param node syntax tree
     * @param depth current depth into the tree
     */
    private void beginTraversing(Node node, int depth){
        if(!node.isRoot()) {
            System.out.print("|  ".repeat(depth));
        }
        System.out.println(node.getData());
        if(!node.isLeaf()){
            for(Node child: node.getChildren()){
                beginTraversing(child, depth + 1);
            }
        }
    }

    /**
     * When called the driver begins traversing the syntax tree.
     *
     */
    public void traverseAST(){
        beginTraversing(syntaxTree, 0);
    }

    /**
     * Driver implements visitor pattern for more functionality.
     * More precisely, the generated AST is used to derive the different rules
     * of a given LL(1) grammar.
     *
     * @param visitor Visitor
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visitNode(syntaxTree);
    }
}
