package com.example.statemachine.state;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WinnerState implements State {
    private final GumballMachineV2 gumballMachine;
    
    @Override
    public void insertQuarter() {
        System.out.println("Please wait, dispensing gumballs");
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
        System.out.println("YOU'RE A WINNER! You get two gumballs for your quarter");
        gumballMachine.releaseBall();
        if (gumballMachine.getGumballCount() == 0) {
            gumballMachine.setState(gumballMachine.getSoldOutState());
        } else {
            gumballMachine.releaseBall();
            if (gumballMachine.getGumballCount() > 0) {
                gumballMachine.setState(gumballMachine.getNoQuarterState());
            } else {
                System.out.println("Out of gumballs");
                gumballMachine.setState(gumballMachine.getSoldOutState());
            }
        }
    }
}