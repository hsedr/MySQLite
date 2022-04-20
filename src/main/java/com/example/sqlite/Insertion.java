package com.example.sqlite;

public class Insertion implements Statement{
    @Override
    public void execute() {
        System.out.println("This is where we would do an insertion");
        System.out.println("Executed.");
    }
}
