package com.example.sqlite.utils;

import java.util.*;

public class Table {

    HashMap<Pair<String, String>, String> table = new HashMap<>();

    /**
     * Inserts a value at specified row and column.
     *
     * @param key1 row
     * @param key2 column
     * @param value value to be inserted
     */
    public void insertValue(String key1, String key2, String value){
        table.put(new Pair<>(key1, key2), value);
    }

    /**
     * Returns the value at specified row and column.
     * If there is no entry for this pair of keys an empty
     * ArrayList is returned.
     *
     * @param key1 row
     * @param key2 column
     * @return result as ArrayList
     */
    public ArrayList<String> getValue(String key1, String key2){
        Pair<String, String> key = new Pair<>(key1, key2);
        ArrayList<String> result = new ArrayList<>();
        String value = "";
        value = table.get(key);
        if(value != null) {
            result.addAll(Arrays.asList(value.split(" ")));
        }
        return result;
    }
}
