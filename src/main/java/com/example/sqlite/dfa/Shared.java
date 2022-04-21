package com.example.sqlite.dfa;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Contains information shared across all DFAs.
 * Attributes and tables are counted as elements of the Σ Alphabet
 */
public abstract class Shared {

    static ArrayList<String> attributes = new ArrayList<>(Arrays.asList("column"));
    static ArrayList<String> tables = new ArrayList<>(Arrays.asList("table"));

    public void addColumn(String c){
        attributes.add(c);
    }

    public ArrayList<String> getColumns(){
        return attributes;
    }

    public boolean containsColumn(String c){
        return attributes.contains(c);
    }

    public void addTable(String t){
        tables.add(t);
    }

    public boolean containsTable(String t){
        return tables.contains(t);
    }

}
