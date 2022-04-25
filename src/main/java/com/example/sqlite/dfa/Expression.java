package com.example.sqlite.dfa;

import java.util.ArrayList;
import java.util.List;

public class Expression implements DFAutomaton{

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
                return new AndCondition().matches(character) ? 1 : TRAP;
            case 1:
                return character.equals("or") ? 0 : TRAP;
            case TRAP:
                return TRAP;
        }
        return 0;
    }

    private Object[] parseWord(String word){
        ArrayList<String> parsed = new ArrayList<>();
        parsed.addAll(List.of(word.split("(?=or )|(?<= or)")));
        parsed.removeIf(item -> item == null || " ".equals(item));
        return parsed.toArray();
    }
}
