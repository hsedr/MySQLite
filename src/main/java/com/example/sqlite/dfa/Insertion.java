package com.example.sqlite.dfa;

public class Insertion extends Shared implements Statement {

    @Override
    public void execute(String statement) {
        System.out.println("This is where we would do an insertion");
        System.out.println("Executed.");
    }

    @Override
    public boolean matches(String word) {
        return false;
    }

    @Override
    public int nextState(int currentState, String character) {
        return 0;
    }
}
