package com.example.sqlite.dfa;
/**
 * Interface for a deterministic finite automata
 */
public interface Statement {

    //interprets entered statement
    void execute(String statement);

    //accepts or rejects an entered String
    boolean matches(String word);

    //transition function => δ: Q x Σ -> Q
    int nextState(int currentState, String character);
}
