package com.simultechnology.statemachine.state.impl;

import com.simultechnology.statemachine.state.GumballMachineV2;
import com.simultechnology.statemachine.state.State;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NoQuarterState implements State {
    private final GumballMachineV2 gumballMachine;
    
    @Override
    public void insertQuarter() {
        System.out.println("Quarter accepted");
        gumballMachine.setState(gumballMachine.getHasQuarterState());
    }
    
    @Override
    public void ejectQuarter() {
        System.out.println("No quarter to return");
    }
    
    @Override
    public void turnCrank() {
        System.out.println("Please insert a quarter first");
    }
    
    @Override
    public void dispense() {
        System.out.println("Please insert quarter first");
    }
}