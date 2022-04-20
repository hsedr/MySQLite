package com.example.sqlite;

import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        while(true){
            printPrompt();
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine().trim();
            //System.out.println(input);
            String head = input.split("")[0];
            if (head.equals(".")){
                switch (doMetaCommand(input)){
                    case (0):
                        continue;
                    case (-1):
                        System.out.printf("Unrecognized command '%s' %n", input);
                        continue;
                }
            }
            switch (prepareStatement(input)){
                case (0):
                    break;
                case (-1):
                    System.out.printf("Unrecognized key word at start of '%s' %n", input);
                    continue;
            }
        }
    }

    private static void printPrompt(){
        System.out.print("db > ");
    }

    private static int doMetaCommand(String command){
        if(command.equals(".exit")){
            System.exit(0);
            return 0;
        }
        return -1;
    }

    private static int prepareStatement(String input){
        Statement stmt;
        if (input.startsWith("insert")){
            stmt = new Insertion();
            stmt.execute();
            return 0;
        }
        if (input.startsWith("select")){
            stmt = new Selection();
            stmt.execute();
            return 0;
        }
        return -1;
    }

}
