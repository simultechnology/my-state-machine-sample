package com.simultechnology.statemachine;

public class Main {
    public static void main(String[] args) {
        GumballMachine gumballMachine = new GumballMachine(10);
        
        System.out.println("=== Initial State ===");
        System.out.println(gumballMachine);
        
        // Try multiple times to demonstrate both regular and winner cases
        for (int i = 0; i < 5; i++) {
            System.out.println("\n=== Attempt " + (i + 1) + " ===");
            System.out.println("Inserting quarter...");
            gumballMachine.insertQuarter();
            
            System.out.println("Turning crank...");
            gumballMachine.turnCrank();
            
            System.out.println(gumballMachine);
        }
    }
}