package com.example.sqlite.dfa;

import java.util.ArrayList;
import java.util.List;

public class Factor implements DFAutomaton{

    private static final int BEGINNING_STATE = 0;
    private static final int END_STATE = 1;
    private static final int TRAP = 2;

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
        switch(currentState){
            case 0:
                return new Term().matches(character) ? 1 : TRAP;
            case 1:
                if(character.equals("*") || character.equals("/")) return 0;
                else return TRAP;
            case TRAP:
                return TRAP;
        }
        return -1;
    }

    private Object[] parseWord(String word){
        ArrayList<String> parsed = new ArrayList<>();
        if(word.contains("*") || word.contains("/")){
            parsed.addAll(List.of(word.split("(?=\\*)|(?<=\\*)|(?=/)|(?<=/)")));
            parsed.removeIf(item -> item == null || " ".equals(item));
        }
        else return new Object[]{word};
        return parsed.toArray();
    }

}
