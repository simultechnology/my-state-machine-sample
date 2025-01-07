package com.example.statemachine;

public enum VendingMachineEvent {
    INSERT_QUARTER,       // 25セントを投入する
    TURN_CRANK,          // ハンドルを回す
    DISPENSE,            // ガムボールを出す
    REFILL               // ガムボールを補充
}