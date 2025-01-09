package com.simultechnology.statemachine.state.impl;

import com.simultechnology.statemachine.state.GumballMachineV2;
import com.simultechnology.statemachine.state.State;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SoldState implements State {
    private final GumballMachineV2 gumballMachine;
    
    @Override
    public void insertQuarter() {
        System.out.println("Please wait, dispensing gumball");
    }
    
    @Override
    public void ejectQuarter() {
        System.out.println("Sorry, already turned the crank");
    }
    
    @Override
    public void turnCrank() {
        System.out.println("Turning twice doesn't give you another gumball");
    }
    
    @Override
    public void dispense() {
        gumballMachine.releaseBall();
        if (gumballMachine.getGumballCount() > 0) {
            gumballMachine.setState(gumballMachine.getNoQuarterState());
        } else {
            System.out.println("Out of gumballs");
            gumballMachine.setState(gumballMachine.getSoldOutState());
        }
    }
}