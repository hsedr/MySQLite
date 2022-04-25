package com.example.sqlite.dfa;

import java.util.ArrayList;
import java.util.List;

public class AndCondition implements DFAutomaton{

    private static final int BEGINNING_STATE = 0;
    private static final int END_STATE = 1;
    private static final int TRAP = 2;

    @Override
    public boolean matches(String word) {
        int state = BEGINNING_STATE;
        for(Object character: parseWord(word)){
            System.out.println(character);
            state = this.nextState(state, String.valueOf(character));
        }
        return state == END_STATE;
    }

    @Override
    public int nextState(int currentState, String character) {
        switch(currentState){
            case 0:
                return new Condition().matches(character) ? 1 : TRAP;
            case 1:
                return character.equals("and") ? 0 : TRAP;
            case TRAP:
                return TRAP;
        }
        return -1;
    }

    private Object[] parseWord(String word) {
        ArrayList<String> parsed = new ArrayList<>();
        if(!word.matches("(?<!between{0,20})and")){
            System.out.println("Hello");
            parsed.addAll(List.of(word.split("(?=and )|(?<= and)")));
        }
        else parsed.addAll(List.of(word.split("(?=\\W+)|(?<=\\W)")));
        parsed.removeIf(item -> item == null || " ".equals(item));
        return parsed.toArray();
    }

}
