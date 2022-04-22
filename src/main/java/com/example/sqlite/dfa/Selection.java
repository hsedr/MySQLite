package com.example.sqlite.dfa;

import java.util.ArrayList;
import java.util.List;

public class Selection extends Shared implements DFAutomaton {

    final static int BEGINNING_STATE = 0;
    final static int END_STATE = 7;

    /**
     * Interprets statement.
     * //TODO
     *
     * @param statement entered statment
     */
    @Override
    public void execute(String statement) {
        //TODO
        System.out.println("Word is an element of SQL");
        System.out.println("Executed.");
    }

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
     * following state
     * @param currentState state the DFA is currently at
     * @param character    current char
     * @return δ: Q x Σ -> Q
     */
    @Override
    public int nextState(int currentState, String character) {
        switch (currentState) {
            case 0:
                return character.equals("select") ? 1 : 8;
            case 1:
                if (character.equals("*")) return 2;
                if (new Name().matches(character)) return 3;
                else return 8;
            case 2:
                return character.equals("from") ? 4 : 8;
            case 3:
                if (character.equals("from")) return 4;
                if (character.equals(",")) return 5;
                else return 8;
            case 4:
                return new Name().matches(character) ? 6 : 8;
            case 5:
                return new Name().matches(character) ? 3 : 8;
            case 6:
                return character.equals(";") ? 7 : 8;
            case 8:
                return 8;
        }
        return -1;
    }

    /**
     * Splits the String into each unique character of Σ (Alphabet).
     * @param word entered String
     * @return Array of chars
     */
    public Object[] parseWord(String word) {
        if (word.charAt(word.length() - 1) == ';') {
            word = word.substring(0, word.length() - 1);
        }
        ArrayList<String> result = new ArrayList<>(List.of(word.split("\\s+")));
        int index = 1;
        if (!result.get(index).equals("*")) {
            String[] columns = result.get(index).split("((?<=,)|(?=,))");
            result.remove(index);
            for (String str : columns) {
                result.add(index, str);
                index++;
            }
        }
        result.add(";");
        return result.toArray();
    }
}


