package com.example.statemachine;

public enum VendingMachineState {
    NO_GUMBALL,           // ガムボールなし
    NO_QUARTER,           // 25セント未受領
    HAS_QUARTER,          // 25セント受領
    GUMBALL_SOLD          // ガムボール販売
}