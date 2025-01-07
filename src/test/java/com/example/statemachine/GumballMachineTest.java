package com.example.statemachine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class GumballMachineTest {
    private GumballMachine gumballMachine;

    @BeforeEach
    void setUp() {
        gumballMachine = new GumballMachine(5);
    }

    @Test
    @DisplayName("Initial state should be NO_QUARTER with gumballs")
    void testInitialState() {
        assertEquals(VendingMachineState.NO_QUARTER, gumballMachine.getState());
        assertEquals(5, gumballMachine.getGumballCount());
    }

    @Test
    @DisplayName("Initial state should be NO_GUMBALL when created with zero gumballs")
    void testInitialStateWithNoGumballs() {
        GumballMachine emptyMachine = new GumballMachine(0);
        assertEquals(VendingMachineState.NO_GUMBALL, emptyMachine.getState());
        assertEquals(0, emptyMachine.getGumballCount());
    }

    @Test
    @DisplayName("Should accept quarter when in NO_QUARTER state")
    void testInsertQuarter() {
        gumballMachine.insertQuarter();
        assertEquals(VendingMachineState.HAS_QUARTER, gumballMachine.getState());
    }

    @Test
    @DisplayName("Should eject quarter when in HAS_QUARTER state")
    void testEjectQuarter() {
        gumballMachine.insertQuarter();
        gumballMachine.ejectQuarter();
        assertEquals(VendingMachineState.NO_QUARTER, gumballMachine.getState());
    }

    @Test
    @DisplayName("Should not change state when ejecting quarter in NO_QUARTER state")
    void testEjectQuarterInNoQuarterState() {
        gumballMachine.ejectQuarter();
        assertEquals(VendingMachineState.NO_QUARTER, gumballMachine.getState());
    }

    @Test
    @DisplayName("Should dispense gumball when turning crank in HAS_QUARTER state")
    void testTurnCrank() {
        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();
        assertEquals(VendingMachineState.NO_QUARTER, gumballMachine.getState());
        assertEquals(4, gumballMachine.getGumballCount());
    }

    @Test
    @DisplayName("Should not dispense gumball when turning crank without quarter")
    void testTurnCrankWithoutQuarter() {
        gumballMachine.turnCrank();
        assertEquals(VendingMachineState.NO_QUARTER, gumballMachine.getState());
        assertEquals(5, gumballMachine.getGumballCount());
    }

    @Test
    @DisplayName("Should transition to NO_GUMBALL state when last gumball is sold")
    void testLastGumballSold() {
        GumballMachine singleGumballMachine = new GumballMachine(1);
        singleGumballMachine.insertQuarter();
        singleGumballMachine.turnCrank();
        assertEquals(VendingMachineState.NO_GUMBALL, singleGumballMachine.getState());
        assertEquals(0, singleGumballMachine.getGumballCount());
    }

    @Test
    @DisplayName("Should refill gumballs and transition to NO_QUARTER state")
    void testRefill() {
        GumballMachine emptyMachine = new GumballMachine(0);
        emptyMachine.refill(3);
        assertEquals(VendingMachineState.NO_QUARTER, emptyMachine.getState());
        assertEquals(3, emptyMachine.getGumballCount());
    }

    @Test
    @DisplayName("Complete buying sequence should work correctly")
    void testCompleteSequence() {
        // Insert quarter
        gumballMachine.insertQuarter();
        assertEquals(VendingMachineState.HAS_QUARTER, gumballMachine.getState());

        // Turn crank
        gumballMachine.turnCrank();
        assertEquals(VendingMachineState.NO_QUARTER, gumballMachine.getState());
        assertEquals(4, gumballMachine.getGumballCount());

        // Insert quarter again
        gumballMachine.insertQuarter();
        assertEquals(VendingMachineState.HAS_QUARTER, gumballMachine.getState());

        // Eject quarter
        gumballMachine.ejectQuarter();
        assertEquals(VendingMachineState.NO_QUARTER, gumballMachine.getState());
    }

    @Test
    @DisplayName("Should handle invalid operations gracefully")
    void testInvalidOperations() {
        // Try to turn crank without quarter
        gumballMachine.turnCrank();
        assertEquals(VendingMachineState.NO_QUARTER, gumballMachine.getState());
        assertEquals(5, gumballMachine.getGumballCount());

        // Try to eject quarter when none inserted
        gumballMachine.ejectQuarter();
        assertEquals(VendingMachineState.NO_QUARTER, gumballMachine.getState());

        // Insert quarter and try to insert another
        gumballMachine.insertQuarter();
        gumballMachine.insertQuarter();
        assertEquals(VendingMachineState.HAS_QUARTER, gumballMachine.getState());
    }
}