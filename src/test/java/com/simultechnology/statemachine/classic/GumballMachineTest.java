package com.simultechnology.statemachine.classic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.lang.reflect.Field;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GumballMachineTest {
    private GumballMachine gumballMachine;

    @BeforeEach
    void setUp() {
        gumballMachine = new GumballMachine(5);
    }

    private void setRandom(GumballMachine machine, Random mockRandom) throws Exception {
        Field randomField = GumballMachine.class.getDeclaredField("random");
        randomField.setAccessible(true);
        randomField.set(machine, mockRandom);
    }

    @Test
    @DisplayName("Should become winner when random value is 0")
    void testWinnerCase() throws Exception {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(10)).thenReturn(0);
        setRandom(gumballMachine, mockRandom);

        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();

        assertEquals(3, gumballMachine.getGumballCount());
        assertEquals(VendingMachineState.NO_QUARTER, gumballMachine.getState());
    }

    @Test
    @DisplayName("Should handle winner case with only one gumball left")
    void testWinnerWithOneGumball() throws Exception {
        GumballMachine machine = new GumballMachine(1);
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(10)).thenReturn(0);
        setRandom(machine, mockRandom);

        machine.insertQuarter();
        machine.turnCrank();

        assertEquals(0, machine.getGumballCount());
        assertEquals(VendingMachineState.NO_GUMBALL, machine.getState());
    }

    @Test
    @DisplayName("Should not be winner when random value is not 0")
    void testNonWinnerCase() throws Exception {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(10)).thenReturn(1);
        setRandom(gumballMachine, mockRandom);

        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();

        assertEquals(4, gumballMachine.getGumballCount());
        assertEquals(VendingMachineState.NO_QUARTER, gumballMachine.getState());
    }

    @Test
    @DisplayName("Should correctly format toString for winner state")
    void testToStringWinnerState() throws Exception {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(10)).thenReturn(0);
        setRandom(gumballMachine, mockRandom);

        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();

        String status = gumballMachine.toString();
        assertTrue(status.contains("3 gumballs"));
    }
}