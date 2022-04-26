package com.example.sqlite.dfa;

import java.util.ArrayList;
import java.util.List;

public class Selection extends Shared implements DFAutomaton {

    final static int BEGINNING_STATE = 0;
    final static int END_STATE = 7;
    final static int TRAP = 10;

    /**
     * Accepts or rejects an entered String.
     * For acceptation the last state must be the END_STATE.
     * @param word entered word
     * @return true if String is an element of "SQL", else false
     */
    @Override
    public boolean matches(String word) {
        int state = BEGINNING_STATE;
        for (Object character : parseWord(word)) {
            state = this.nextState(state, String.valueOf(character));
        }
        return state == END_STATE;
    }

    /**
     * Takes current state and a character and determines
     * following state.
     * @param currentState state the DFA is currently at
     * @param character    current char
     * @return δ: Q x Σ -> Q
     */
    @Override
    public int nextState(int currentState, String character) {
        switch (currentState) {
            case 0:
                return character.equals("select") ? 1 : TRAP;
            case 1:
                if (character.equals("all")) return 2;
                if (new Name().matches(character)) return 3;
                else return TRAP;
            case 2:
                return character.equals("from") ? 4 : TRAP;
            case 3:
                if (character.equals("from")) return 4;
                if (character.equals(",")) return 5;
                else return TRAP;
            case 4:
                return new Name().matches(character) ? 6 : TRAP;
            case 5:
                return new Name().matches(character) ? 3 : TRAP;
            case 6:
                if(character.equals(";")) return 7;
                else if(character.equals("where")) return 8;
                else return TRAP;
            case 7:
            case TRAP:
                return TRAP;
            case 8:
                return new Expression().matches(character) ? 9 : TRAP;
            case 9:
                if(character.equals(";")) return 7;
                else return TRAP;
        }
        return -1;
    }

    /**
     * Sort of a Tokenizer.
     * Splits the String into each unique character of Σ (Alphabet).
     * @param word entered String
     * @return Array of chars
     */
    public Object[] parseWord(String word) {
        ArrayList<String> parsed = new ArrayList<>();
        String[] split = word.split("(?<= where)", 2);
        parsed.addAll(List.of(split[0].split("(?<=\\W)|(?=\\W+)")));
        if(split.length >= 2) parsed.addAll(List.of((split[1].split("(?=;)"))));
        parsed.removeIf(item -> item == null || " ".equals(item));
        return parsed.toArray();
    }
}


