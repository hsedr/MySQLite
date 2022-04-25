package com.example.sqlite.dfa;

import java.util.ArrayList;
import java.util.List;

public class Insertion extends Shared implements DFAutomaton {

    final static int BEGINNING_STATE = 0;
    final static int END_STATE = 10;

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
        return 0;
    }

    public Object[] parseWord(String word){
        if(word.charAt(word.length()-1) == ';'){
            word = word.substring(0, word.length()-1);
        }
        ArrayList<String> result = new ArrayList<>(List.of(word.split("\\s+")));
        int index = 1;
        if(!result.get(index).equals("*")) {
            String[] columns = result.get(index).split("((?<=,)|(?=,))");
            result.remove(index);
            for (String str : columns){
                result.add(index, str);
                index++;
            }
        }
        result.add(";");
        return result.toArray();
    }
}
