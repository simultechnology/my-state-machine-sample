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
                System.out.println("25セントを受け取りました");
                break;
            case HAS_QUARTER:
                System.out.println("すでに25セントが投入されています");
                break;
            case NO_GUMBALL:
                System.out.println("ガムボールがありません。25セントを返却します");
                break;
            case GUMBALL_SOLD:
                System.out.println("お待ちください。ガムボールを出しています");
                break;
        }
    }

    public void ejectQuarter() {
        switch (currentState) {
            case HAS_QUARTER:
                System.out.println("25セントを返却します");
                currentState = VendingMachineState.NO_QUARTER;
                break;
            case NO_QUARTER:
                System.out.println("25セントが投入されていません");
                break;
            case GUMBALL_SOLD:
                System.out.println("すでにハンドルを回しています");
                break;
            case NO_GUMBALL:
                System.out.println("25セントが投入されていません");
                break;
        }
    }
    
    public void turnCrank() {
        switch (currentState) {
            case HAS_QUARTER:
                System.out.println("ハンドルを回しました");
                currentState = VendingMachineState.GUMBALL_SOLD;
                dispense();
                break;
            case NO_QUARTER:
                System.out.println("25セントを投入してください");
                break;
            case NO_GUMBALL:
                System.out.println("ガムボールがありません");
                break;
            case GUMBALL_SOLD:
                System.out.println("2回回してもガムボールは出ません");
                break;
        }
    }
    
    private void dispense() {
        switch (currentState) {
            case GUMBALL_SOLD:
                System.out.println("ガムボールが出ます");
                gumballCount--;
                if (gumballCount == 0) {
                    System.out.println("ガムボールがなくなりました");
                    currentState = VendingMachineState.NO_GUMBALL;
                } else {
                    currentState = VendingMachineState.NO_QUARTER;
                }
                break;
            default:
                System.out.println("先に25セントを投入してください");
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
}