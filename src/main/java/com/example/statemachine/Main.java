package com.example.statemachine;

public class Main {
    public static void main(String[] args) {
        // Create a gumball machine with 5 gumballs
        GumballMachine gumballMachine = new GumballMachine(5);
        
        System.out.println("=== Initial State ===");
        System.out.println("Gumball count: " + gumballMachine.getGumballCount());
        System.out.println("Current state: " + gumballMachine.getState());
        
        System.out.println("\n=== Insert Quarter ===");
        gumballMachine.insertQuarter();
        
        System.out.println("\n=== Eject Quarter ===");
        gumballMachine.ejectQuarter();
        
        System.out.println("\n=== Insert Quarter Again ===");
        gumballMachine.insertQuarter();
        
        System.out.println("\n=== Turn Crank ===");
        gumballMachine.turnCrank();
        
        System.out.println("\n=== Current State ===");
        System.out.println("Gumball count: " + gumballMachine.getGumballCount());
        System.out.println("Current state: " + gumballMachine.getState());
        
        // Try additional operations
        System.out.println("\n=== Try to Turn Crank Without Quarter ===");
        gumballMachine.turnCrank();
        
        System.out.println("\n=== Insert Quarter ===");
        gumballMachine.insertQuarter();
        
        System.out.println("\n=== Try to Insert Quarter Again ===");
        gumballMachine.insertQuarter();
    }
}