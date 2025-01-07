package com.example.statemachine;

public class GumballMachine {
    private VendingMachineState currentState;
    private int gumballCount;
    
    public GumballMachine(int numberOfGumballs) {
        this.gumballCount = numberOfGumballs;
        if (numberOfGumballs > 0) {
            currentState = VendingMachineState.NO_QUARTER;
        } else {
            currentState = VendingMachineState.NO_GUMBALL;
        }
    }
    
    public void insertQuarter() {
        switch (currentState) {
            case NO_QUARTER:
                currentState = VendingMachineState.HAS_QUARTER;
                System.out.println("Quarter accepted");
                break;
            case HAS_QUARTER:
                System.out.println("Quarter already inserted");
                break;
            case NO_GUMBALL:
                System.out.println("No gumballs available. Returning quarter");
                break;
            case GUMBALL_SOLD:
                System.out.println("Please wait, dispensing gumball");
                break;
        }
    }

    public void ejectQuarter() {
        switch (currentState) {
            case HAS_QUARTER:
                System.out.println("Quarter returned");
                currentState = VendingMachineState.NO_QUARTER;
                break;
            case NO_QUARTER:
                System.out.println("No quarter to return");
                break;
            case GUMBALL_SOLD:
                System.out.println("Crank already turned");
                break;
            case NO_GUMBALL:
                System.out.println("No quarter to return");
                break;
        }
    }
    
    public void turnCrank() {
        switch (currentState) {
            case HAS_QUARTER:
                System.out.println("Crank turned");
                currentState = VendingMachineState.GUMBALL_SOLD;
                dispense();
                break;
            case NO_QUARTER:
                System.out.println("Please insert a quarter first");
                break;
            case NO_GUMBALL:
                System.out.println("No gumballs available");
                break;
            case GUMBALL_SOLD:
                System.out.println("Turning twice doesn't give you another gumball");
                break;
        }
    }
    
    private void dispense() {
        switch (currentState) {
            case GUMBALL_SOLD:
                System.out.println("Dispensing gumball");
                gumballCount--;
                if (gumballCount == 0) {
                    System.out.println("Out of gumballs");
                    currentState = VendingMachineState.NO_GUMBALL;
                } else {
                    currentState = VendingMachineState.NO_QUARTER;
                }
                break;
            default:
                System.out.println("Please insert quarter first");
                break;
        }
    }
    
    public void refill(int count) {
        this.gumballCount += count;
        if (currentState == VendingMachineState.NO_GUMBALL) {
            currentState = VendingMachineState.NO_QUARTER;
        }
    }
    
    public VendingMachineState getState() {
        return currentState;
    }
    
    public int getGumballCount() {
        return gumballCount;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Inventory: ").append(gumballCount).append(" gumball");
        if (gumballCount != 1) {
            result.append("s");
        }
        result.append("\n");

        result.append("Machine is ");
        switch (currentState) {
            case NO_QUARTER:
                result.append("waiting for quarter");
                break;
            case HAS_QUARTER:
                result.append("waiting for crank to be turned");
                break;
            case GUMBALL_SOLD:
                result.append("delivering a gumball");
                break;
            case NO_GUMBALL:
                result.append("out of gumballs");
                break;
        }

        return result.toString();
    }
}