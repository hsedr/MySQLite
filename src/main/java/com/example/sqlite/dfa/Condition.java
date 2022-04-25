package com.example.sqlite.dfa;

import java.util.ArrayList;
import java.util.List;

public class Condition implements DFAutomaton{

    final static int BEGINNING_STATE = 0;
    final static int END_STATE = 3;
    final static int TRAP = 9;

    @Override
    public boolean matches(String word) {
        int state = BEGINNING_STATE;
        for(Object character: parseWord(word)){
            System.out.println(word);
            state = this.nextState(state, String.valueOf(character));
        }
        System.out.println("end state : " +  state);
        return state == END_STATE;
    }

    @Override
    public int nextState(int currentState, String character) {
        switch(currentState){
            case 0:
                return new Operand().matches(character) ? 1 : TRAP;
            case 1:
                if(new Compare().matches(character)) return 2;
                else if(character.equals("is")) return 5;
                else if(character.equals("not")) return 4;
                else if(character.equals("between")) return 7;
                else return TRAP;
            case 2:
                return new Operand().matches(character) ? 3 : TRAP;
            case 3:
            case TRAP:
                return TRAP;
            case 4:
                if(character.equals("like")) return 2;
                else if(character.equals("between")) return 7;
                else return TRAP;
            case 5:
                if(character.equals("not")) return 6;
                else if(character.equals("null")) return 3;
                else return TRAP;
            case 6:
                return character.equals("null") ? 3 : TRAP;
            case 7:
                return new Operand().matches(character) ? 8 : TRAP;
            case 8:
                return character.equals("and") ? 2 : TRAP;
        }
        return -1;
    }

    private Object[] parseWord(String word){
        ArrayList<String> parsed = new ArrayList<>();
        parsed.addAll(List.of(word.split("(?=[\\W+ && [^']])|(?<=[\\W && [^']])")));
        parsed.removeIf(item -> item == null || " ".equals(item));
        return parsed.toArray();
    }

}
