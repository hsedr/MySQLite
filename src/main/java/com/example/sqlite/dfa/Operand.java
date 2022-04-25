package com.example.sqlite.dfa;

import java.util.ArrayList;
import java.util.List;

public class Operand implements DFAutomaton{

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
        switch(currentState){
            case 0:
                return new Summand().matches(character) ? 1 : TRAP;
            case 1:
                if(character.equals("concat")) return 0;
                else return TRAP;
            case TRAP:
                return TRAP;
        }
        return -1;
    }

    private Object[] parseWord(String word) {
        ArrayList<String> parsed = new ArrayList<>();
        if(word.contains("concat")){
            parsed.addAll(List.of(word.split("(?=concat)|(?<=concat)")));
            parsed.removeIf(item -> item == null || " ".equals(item));
        }
        else return new Object[]{word};
        return parsed.toArray();
    }
}
