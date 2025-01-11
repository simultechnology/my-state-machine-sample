package com.simultechnology.statemachine.state;

import com.simultechnology.statemachine.state.impl.*;
import lombok.Getter;

@Getter
public class GumballMachineV2 {
    private final State noQuarterState;
    private final State hasQuarterState;
    private final State soldState;
    private final State winnerState;
    private final State soldOutState;
    
    private State currentState;
    private int gumballCount = 0;
    
    public GumballMachineV2(int numberGumballs) {
        noQuarterState = new NoQuarterState(this);
        hasQuarterState = new HasQuarterState(this);
        soldState = new SoldState(this);
        winnerState = new WinnerState(this);
        soldOutState = new SoldOutState(this);

        this.gumballCount = numberGumballs;
        if (numberGumballs > 0) {
            currentState = noQuarterState;
        } else {
            currentState = soldOutState;
        }
    }
    
    public void insertQuarter() {
        currentState.insertQuarter();
    }
    
    public void ejectQuarter() {
        currentState.ejectQuarter();
    }
    
    public void turnCrank() {
        currentState.turnCrank();
        currentState.dispense();
    }

    public void setState(State state) {
        this.currentState = state;
    }
    
    public void releaseBall() {
        if (gumballCount > 0) {
            System.out.println("A gumball comes rolling out...");
            gumballCount--;
        }
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Inventory: ").append(gumballCount).append(" gumball");
        if (gumballCount != 1) {
            result.append("s");
        }
        result.append("\n");
        result.append("Machine is ").append(currentState.getClass().getSimpleName());
        return result.toString();
    }
}