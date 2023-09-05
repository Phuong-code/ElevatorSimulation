package com.fdm.elevator;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

class ShedulerTest {

    @Test
    public void testChooseElevachooseElevator_WithElevatorInSameDirectionUP_ReturnsClosestElevatortor() {
        // Create mock Elevator instances
        Elevator elevator1 = Mockito.mock(Elevator.class);
        Elevator elevator2 = Mockito.mock(Elevator.class);
        Elevator elevator3 = Mockito.mock(Elevator.class);

        // Set up the mock Elevator instances
        Mockito.when(elevator1.getCurrentFloor()).thenReturn(1);
        Mockito.when(elevator1.getCurrentState()).thenReturn(ElevatorState.UP);
        Mockito.when(elevator2.getCurrentFloor()).thenReturn(4);
        Mockito.when(elevator2.getCurrentState()).thenReturn(ElevatorState.STATIONARY);
        Mockito.when(elevator3.getCurrentFloor()).thenReturn(9);
        Mockito.when(elevator3.getCurrentState()).thenReturn(ElevatorState.DOWN);

        // Create a list of mock Elevator instances
        List<Elevator> elevators = Arrays.asList(elevator1, elevator2, elevator3);

        // Create the Scheduler instance with the mock Elevator list
        Scheduler scheduler = new Scheduler(elevators);

        // Perform the chooseElevator method call
        Elevator chosenElevator = scheduler.chooseElevator("4:7");

        // Verify that the chosenElevator is the expected mock elevator
        assertEquals(elevator1, chosenElevator);

	}
    
    @Test
    public void testChooseElevachooseElevator_WithElevatorAllStationary_ReturnsClosestElevatortor() {
        // Create mock Elevator instances
        Elevator elevator1 = Mockito.mock(Elevator.class);
        Elevator elevator2 = Mockito.mock(Elevator.class);
        Elevator elevator3 = Mockito.mock(Elevator.class);

        // Set up the mock Elevator instances
        Mockito.when(elevator1.getCurrentFloor()).thenReturn(3);
        Mockito.when(elevator1.getCurrentState()).thenReturn(ElevatorState.STATIONARY);
        Mockito.when(elevator2.getCurrentFloor()).thenReturn(6);
        Mockito.when(elevator2.getCurrentState()).thenReturn(ElevatorState.STATIONARY);
        Mockito.when(elevator3.getCurrentFloor()).thenReturn(1);
        Mockito.when(elevator3.getCurrentState()).thenReturn(ElevatorState.STATIONARY);

        // Create a list of mock Elevator instances
        List<Elevator> elevators = Arrays.asList(elevator1, elevator2, elevator3);

        // Create the Scheduler instance with the mock Elevator list
        Scheduler scheduler = new Scheduler(elevators);

        // Perform the chooseElevator method call
        Elevator chosenElevator = scheduler.chooseElevator("4:7");

        // Verify that the chosenElevator is the expected mock elevator
        assertEquals(elevator1, chosenElevator);

	}
    
    @Test
    public void testChooseElevachooseElevator_IfNoElevetorSameDirection_ReturnsClosestElevatortor() {
        // Create mock Elevator instances
        Elevator elevator1 = Mockito.mock(Elevator.class);
        Elevator elevator2 = Mockito.mock(Elevator.class);
        Elevator elevator3 = Mockito.mock(Elevator.class);

        // Set up the mock Elevator instances
        Mockito.when(elevator1.getCurrentFloor()).thenReturn(7);
        Mockito.when(elevator1.getCurrentState()).thenReturn(ElevatorState.UP);
        Mockito.when(elevator2.getCurrentFloor()).thenReturn(1);
        Mockito.when(elevator2.getCurrentState()).thenReturn(ElevatorState.STATIONARY);
        Mockito.when(elevator3.getCurrentFloor()).thenReturn(3);
        Mockito.when(elevator3.getCurrentState()).thenReturn(ElevatorState.DOWN);

        // Create a list of mock Elevator instances
        List<Elevator> elevators = Arrays.asList(elevator1, elevator2, elevator3);

        // Create the Scheduler instance with the mock Elevator list
        Scheduler scheduler = new Scheduler(elevators);

        // Perform the chooseElevator method call
        Elevator chosenElevator = scheduler.chooseElevator("5:7");

        // Verify that the chosenElevator is the expected mock elevator
        assertEquals(elevator2, chosenElevator);

	}
    
    @Test
    public void testChooseElevachooseElevator_IfNoElevetorSameDirection_ReturnsClosestSTATIONARYElevator() {
        // Create mock Elevator instances
        Elevator elevator1 = Mockito.mock(Elevator.class);
        Elevator elevator2 = Mockito.mock(Elevator.class);
        Elevator elevator3 = Mockito.mock(Elevator.class);
        Elevator elevator4 = Mockito.mock(Elevator.class);

        // Set up the mock Elevator instances
        Mockito.when(elevator1.getCurrentFloor()).thenReturn(7);
        Mockito.when(elevator1.getCurrentState()).thenReturn(ElevatorState.UP);
        Mockito.when(elevator2.getCurrentFloor()).thenReturn(1);
        Mockito.when(elevator2.getCurrentState()).thenReturn(ElevatorState.STATIONARY);
        Mockito.when(elevator3.getCurrentFloor()).thenReturn(2);
        Mockito.when(elevator3.getCurrentState()).thenReturn(ElevatorState.STATIONARY);
        Mockito.when(elevator4.getCurrentFloor()).thenReturn(3);
        Mockito.when(elevator4.getCurrentState()).thenReturn(ElevatorState.DOWN);

        // Create a list of mock Elevator instances
        List<Elevator> elevators = Arrays.asList(elevator1, elevator2, elevator3, elevator4);

        // Create the Scheduler instance with the mock Elevator list
        Scheduler scheduler = new Scheduler(elevators);

        // Perform the chooseElevator method call
        Elevator chosenElevator = scheduler.chooseElevator("5:7");

        // Verify that the chosenElevator is the expected mock elevator
        assertEquals(elevator3, chosenElevator);

	}
    
    @Test
    public void testChooseElevachooseElevator_IfNoElevetorSameDirection_ReturnsClosestSTATIONARYElevator2() {
        // Create mock Elevator instances
        Elevator elevator1 = Mockito.mock(Elevator.class);
        Elevator elevator2 = Mockito.mock(Elevator.class);
        Elevator elevator3 = Mockito.mock(Elevator.class);
        Elevator elevator4 = Mockito.mock(Elevator.class);

        // Set up the mock Elevator instances
        Mockito.when(elevator1.getCurrentFloor()).thenReturn(7);
        Mockito.when(elevator1.getCurrentState()).thenReturn(ElevatorState.UP);
        Mockito.when(elevator2.getCurrentFloor()).thenReturn(1);
        Mockito.when(elevator2.getCurrentState()).thenReturn(ElevatorState.STATIONARY);
        Mockito.when(elevator3.getCurrentFloor()).thenReturn(2);
        Mockito.when(elevator3.getCurrentState()).thenReturn(ElevatorState.STATIONARY);
        Mockito.when(elevator4.getCurrentFloor()).thenReturn(3);
        Mockito.when(elevator4.getCurrentState()).thenReturn(ElevatorState.DOWN);

        // Create a list of mock Elevator instances
        List<Elevator> elevators = Arrays.asList(elevator1, elevator2, elevator3, elevator4);

        // Create the Scheduler instance with the mock Elevator list
        Scheduler scheduler = new Scheduler(elevators);

        // Perform the chooseElevator method call
        Elevator chosenElevator = scheduler.chooseElevator("8:4");

        // Verify that the chosenElevator is the expected mock elevator
        assertEquals(elevator3, chosenElevator);

	}
    
    @Test
    public void chooseElevator_WithElevatorInSameDirectionDOWN_ReturnsClosestElevator() {
    	// Create mock Elevator instances
        Elevator elevator1 = Mockito.mock(Elevator.class);
        Elevator elevator2 = Mockito.mock(Elevator.class);
        Elevator elevator3 = Mockito.mock(Elevator.class);

        // Set up the mock Elevator instances
        Mockito.when(elevator1.getCurrentFloor()).thenReturn(3);
        Mockito.when(elevator1.getCurrentState()).thenReturn(ElevatorState.DOWN);
        Mockito.when(elevator2.getCurrentFloor()).thenReturn(6);
        Mockito.when(elevator2.getCurrentState()).thenReturn(ElevatorState.DOWN);
        Mockito.when(elevator3.getCurrentFloor()).thenReturn(2);
        Mockito.when(elevator3.getCurrentState()).thenReturn(ElevatorState.STATIONARY);
        
        // Create a list of mock Elevator instances
        List<Elevator> elevators = Arrays.asList(elevator1, elevator2, elevator3);

        // Create the Scheduler instance with the mock Elevator list
        Scheduler scheduler = new Scheduler(elevators);

        Elevator chosenElevator = scheduler.chooseElevator("5:2");

        assertEquals(elevator2, chosenElevator);
    }
    
    @Test
    public void chooseElevator_WithNoSuitableElevator_ReturnsRandomElevator() {
    	// Create mock Elevator instances
        Elevator elevator1 = Mockito.mock(Elevator.class);
        Elevator elevator2 = Mockito.mock(Elevator.class);
        Elevator elevator3 = Mockito.mock(Elevator.class);
        

        // Set up the mock Elevator instances
        Mockito.when(elevator1.getCurrentFloor()).thenReturn(7);
        Mockito.when(elevator1.getCurrentState()).thenReturn(ElevatorState.UP);
        Mockito.when(elevator2.getCurrentFloor()).thenReturn(6);
        Mockito.when(elevator2.getCurrentState()).thenReturn(ElevatorState.UP);
        Mockito.when(elevator3.getCurrentFloor()).thenReturn(5);
        Mockito.when(elevator3.getCurrentState()).thenReturn(ElevatorState.UP);
        
        // Create a list of mock Elevator instances
        List<Elevator> elevators = Arrays.asList(elevator1, elevator2, elevator3);

        // Create the Scheduler instance with the mock Elevator list
        Scheduler scheduler = new Scheduler(elevators);
        Random random = new Random();

        Elevator chosenElevator = scheduler.chooseElevator("5:2");
        
        // Assert that the chosen elevator is within the range of indices 0 to 2
        assertTrue(elevators.indexOf(chosenElevator) >= 0 && elevators.indexOf(chosenElevator) <= elevators.size());

    }

}
