package com.example.sqlite.dfa;

/**
 * DFA for Column and Table Names
 */
public class Name implements DFAutomaton{

    final static int BEGINNING_STATE = 0;
    final static int END_STATE = 1;

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
        for(Object character: parseWord(word)){
            state = this.nextState(state, String.valueOf(character));
        }
        return state == END_STATE;
    }

    /**
     * Takes current state and a character and determines
     * following state.
     * Does not accept names starting with a number or 
     * that contain an special character.
     * @param currentState state the DFA is currently at
     * @param character    current char
     * @return δ: Q x Σ -> Q
     */
    @Override
    public int nextState(int currentState, String character) {
        switch(currentState){
            case 0:
                return character.matches("[a-zA-Z]") ? 1 : 2;
            case 1:
                return character.matches("[a-zA-Z0-9]") ? 1 : 2;
            case 2:
                return 2;
        }
        return 0;
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
