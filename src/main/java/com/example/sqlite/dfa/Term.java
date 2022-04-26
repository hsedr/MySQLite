package com.example.sqlite.dfa;

import java.util.ArrayList;
import java.util.List;

public class Term implements DFAutomaton{

    private static final int BEGINNING_STATE = 0;
    private static final int END_STATE = 3;
    private static final int TRAP = 4;

    @Override
    public boolean matches(String word) {
        int state = BEGINNING_STATE;
        for(Object character: parseWord(word)){
            state = this.nextState(state, String.valueOf(character));
        }
        return state == END_STATE;
    }

    //TODO varchars and character shall be matched with regular Expression "'[a-zA-Z]" | "[a-zA-Z0-9]*"
    @Override
    public int nextState(int currentState, String character) {
        switch (currentState){
            case 0:
                if(character.equals("'")) return 1;
                if(character.matches("\\d+")) return 3;
                if(character.equals("true") || character.equals("false")) return 3;
                return new Name().matches(character) ? 3 : TRAP;
            case 1:
                return character.matches("(.*)") ? 2 : TRAP;
            case 2:
                return character.equals("'") ? 3 : TRAP;
            case 3:
            case TRAP:
                return TRAP;
        }
        return -1;
    }

    private Object[] parseWord(String word){
        ArrayList<String> parsed = new ArrayList<>();
        parsed.addAll(List.of(word.split("(?=')|(?<=')")));
        parsed.removeIf(item -> item == null || " ".equals(item));
        return parsed.toArray();
    }
}
