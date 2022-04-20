package com.example.sqlite;

public class Selection implements Statement{
    @Override
    public void execute() {
        System.out.println("This is were we would do a selection");
        System.out.println("Executed.");
    }
}
