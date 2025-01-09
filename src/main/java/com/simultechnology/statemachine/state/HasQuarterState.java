package com.simultechnology.statemachine.state;

import lombok.RequiredArgsConstructor;
import java.util.Random;

@RequiredArgsConstructor
public class HasQuarterState implements State {
    private final GumballMachineV2 gumballMachine;
    private final Random random = new Random();
    
    @Override
    public void insertQuarter() {
        System.out.println("Quarter already inserted");
    }
    
    @Override
    public void ejectQuarter() {
        System.out.println("Quarter returned");
        gumballMachine.setState(gumballMachine.getNoQuarterState());
    }
    
    @Override
    public void turnCrank() {
        System.out.println("Crank turned");
        int winner = random.nextInt(10);
        if (winner == 0 && gumballMachine.getGumballCount() > 1) {
            gumballMachine.setState(gumballMachine.getWinnerState());
        } else {
            gumballMachine.setState(gumballMachine.getSoldState());
        }
    }
    
    @Override
    public void dispense() {
        System.out.println("No gumball dispensed");
    }
}