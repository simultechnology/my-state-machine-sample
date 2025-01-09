package com.simultechnology.statemachine.state;

import com.simultechnology.statemachine.state.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.lang.reflect.Field;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GumballMachineV2Test {
    private GumballMachineV2 gumballMachine;

    @BeforeEach
    void setUp() {
        gumballMachine = new GumballMachineV2(5);
    }

    @Test
    @DisplayName("Initial state should be NoQuarterState with gumballs")
    void testInitialState() {
        assertTrue(gumballMachine.getCurrentState() instanceof NoQuarterState);
        assertEquals(5, gumballMachine.getGumballCount());
    }

    @Test
    @DisplayName("Initial state should be SoldOutState when no gumballs")
    void testInitialStateNoGumballs() {
        GumballMachineV2 emptyMachine = new GumballMachineV2(0);
        assertTrue(emptyMachine.getCurrentState() instanceof SoldOutState);
        assertEquals(0, emptyMachine.getGumballCount());
    }

    @Test
    @DisplayName("Should transition to HasQuarterState when quarter inserted")
    void testInsertQuarter() {
        gumballMachine.insertQuarter();
        assertTrue(gumballMachine.getCurrentState() instanceof HasQuarterState);
    }

    @Test
    @DisplayName("Should handle winner case")
    void testWinnerCase() throws Exception {
        gumballMachine.insertQuarter();
        
        // Mock Random in HasQuarterState to force winner
        HasQuarterState hasQuarterState = (HasQuarterState) gumballMachine.getHasQuarterState();
        Field randomField = HasQuarterState.class.getDeclaredField("random");
        randomField.setAccessible(true);
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(10)).thenReturn(0);
        randomField.set(hasQuarterState, mockRandom);

        gumballMachine.turnCrank();

        assertEquals(3, gumballMachine.getGumballCount());
        assertTrue(gumballMachine.getCurrentState() instanceof NoQuarterState);
    }

    @Test
    @DisplayName("Should handle regular case")
    void testRegularCase() throws Exception {
        gumballMachine.insertQuarter();
        
        // Mock Random in HasQuarterState to force non-winner
        HasQuarterState hasQuarterState = (HasQuarterState) gumballMachine.getHasQuarterState();
        Field randomField = HasQuarterState.class.getDeclaredField("random");
        randomField.setAccessible(true);
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(10)).thenReturn(1);
        randomField.set(hasQuarterState, mockRandom);

        gumballMachine.turnCrank();

        assertEquals(4, gumballMachine.getGumballCount());
        assertTrue(gumballMachine.getCurrentState() instanceof NoQuarterState);
    }

    @Test
    @DisplayName("Should handle last gumball case")
    void testLastGumballCase() {
        GumballMachineV2 singleGumballMachine = new GumballMachineV2(1);
        singleGumballMachine.insertQuarter();
        singleGumballMachine.turnCrank();

        assertEquals(0, singleGumballMachine.getGumballCount());
        assertTrue(singleGumballMachine.getCurrentState() instanceof SoldOutState);
    }

    @Test
    @DisplayName("Should format toString properly")
    void testToString() {
        String status = gumballMachine.toString();
        assertTrue(status.contains("5 gumballs"));
        assertTrue(status.contains("NoQuarterState"));
    }
}