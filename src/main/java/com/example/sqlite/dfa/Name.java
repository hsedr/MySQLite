package com.example.sqlite.dfa;

/**
 * DFA for Column and Table Names
 */
public class Name implements DFAutomaton{

    final static int BEGINNING_STATE = 0;
    final static int END_STATE = 1;
    final static int TRAP = 2;

    /**
     * Accepts or rejects an entered String.
     * For acceptation the last state must be the END_STATE.
     * @param word entered word
     * @return true if String is an element of "SQL", else false
     */
    @Override
    public boolean matches(String word) {
        int state = BEGINNING_STATE;
        for(Object character: parseWord(word)){
            state = this.nextState(state, String.valueOf(character));
        }
        return state == END_STATE;
    }

    /**
     * Takes current state and a character and determines
     * following state.
     * Does not accept names starting with a number.
     * @param currentState state the DFA is currently at
     * @param character    current char
     * @return δ: Q x Σ -> Q
     */
    @Override
    public int nextState(int currentState, String character) {
        switch(currentState){
            case 0:
                return character.matches("[a-zA-Z]") ? 1 : TRAP;
            case 1:
                return character.matches("[a-zA-Z0-9]") ? 1 : TRAP;
            case TRAP:
                return TRAP;
        }
        return -1;
    }

    /**
     * Splits a String by each character.
     * @param word Table or Column name
     * @return split name
     */
    private String[] parseWord(String word){
        return word.split("");
    }
}
