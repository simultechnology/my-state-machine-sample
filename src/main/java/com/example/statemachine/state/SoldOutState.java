package com.example.statemachine.state;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SoldOutState implements State {
    private final GumballMachineV2 gumballMachine;
    
    @Override
    public void insertQuarter() {
        System.out.println("Machine is sold out");
    }
    
    @Override
    public void ejectQuarter() {
        System.out.println("Cannot eject, no quarter inserted");
    }
    
    @Override
    public void turnCrank() {
        System.out.println("No gumballs available");
    }
    
    @Override
    public void dispense() {
        System.out.println("No gumball dispensed");
    }
}