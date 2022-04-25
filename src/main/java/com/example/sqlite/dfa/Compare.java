package com.example.sqlite.dfa;

public class Compare implements DFAutomaton{

    final static int BEGINNING_STATE = 0;
    final static int END_STATE = 1;
    final static int TRAP = 2;

    @Override
    public boolean matches(String word) {
        int state = BEGINNING_STATE;
        for(Object character: parseWord(word)){
            state = this.nextState(state, String.valueOf(character));
        }
        return state == END_STATE;
    }

    @Override
    public int nextState(int currentState, String character) {
        switch (currentState){
            case 0:
                if(character.equals("<>")
                        || character.equals("<=")
                        || character.equals(">=")
                        || character.equals("=")
                        || character.equals("<")
                        || character.equals(">")
                        || character.equals("!="))
                    return 1;
                else return TRAP;
            case 1:
            case TRAP:
                return TRAP;
        }
        return -1;
    }

    private String[] parseWord(String word){
        return new String[]{word};
    }

}
