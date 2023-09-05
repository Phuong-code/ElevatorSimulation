package com.fdm.elevator;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ElevatorTest {
	
	private Elevator elevator;
	
	@BeforeEach
	public void setup() {
		elevator = new Elevator(1);
	}

	@Test
	void test_getId_Returns_ElevatorId_forElevator() {
		assertNotNull(elevator);
		
		int expected = 1;
		assertEquals(expected, elevator.getId());
	}
	
	@Test
	void test_getCurrentFloor_and_setCurrectFloor_Returns_currentFloor_forElevator() {
		assertNotNull(elevator);
		
		int expectedFloor = 9;
		elevator.setCurrentFloor(9);
		assertEquals(expectedFloor, elevator.getCurrentFloor());		

	}
	
	@Test
	void test_getCurrentState_and_setCurrentState_Returns_UP_forElevator() {
		assertNotNull(elevator);

		elevator.setCurrentState(ElevatorState.UP);
		assertEquals(ElevatorState.UP, elevator.getCurrentState());
	}
	
	@Test
	void test_getCurrentState_and_setCurrentState_Returns_DOWN_forElevator() {
		assertNotNull(elevator);
		
		elevator.setCurrentState(ElevatorState.DOWN);
		assertEquals(ElevatorState.DOWN, elevator.getCurrentState());
	}
	
	@Test
	void test_getCurrentState_and_setCurrentState_Returns_STATIONARY_forElevator() {
		assertNotNull(elevator);
		
		elevator.setCurrentState(ElevatorState.STATIONARY);
		assertEquals(ElevatorState.STATIONARY, elevator.getCurrentState());
	}
	
	@Test
	void test_getCurrentState_and_setCurrentState_Returns_OPENCLOSE_forElevator() {
		assertNotNull(elevator);
		
		elevator.setCurrentState(ElevatorState.OPENCLOSE);
		assertEquals(ElevatorState.OPENCLOSE, elevator.getCurrentState());
	}
	
	@Test
	void test_getDoorTime_and_setDoorTime_Returns_100() {
		assertNotNull(elevator);
		
		int expectedDoorTime = 100;
		Elevator.setDoorTime(100);
		assertEquals(expectedDoorTime, Elevator.getDoortime());
	}
	
	@Test
	void test_getTravelTime_Returns_100() {
		assertNotNull(elevator);

		int expectedTravelTime = 100;
		Elevator.setTravelTime(100);
		assertEquals(expectedTravelTime, Elevator.getTraveltime());
	}
	
	@Test
	void test_getCommand_and_setCommand_Returns_MOVE_forElevator() {
		assertNotNull(elevator);
		
		elevator.setCommand("move");
		assertEquals("move", elevator.getCommand());
	}
	
	@Test
	void test_getElevatorCommands_and_addElevatorCommand_Returns_size_3_forElevator() {
		assertNotNull(elevator);
		
		elevator.addElevatorCommands("9:5");
		elevator.addElevatorCommands("9:5");
		elevator.addElevatorCommands("9:5");
		
		assertEquals(3, elevator.getElevatorCommands().size());
	}
	
	@Test
	void test_getUpdate_and_setUpdate_Returns_true_forElevator() {
		assertNotNull(elevator);
		
		elevator.setUpdate(true);
		assertTrue(elevator.getUpdate());
	}
	
	@Test
	void test_getLoaded_and_setLoaded_Returns_true_forElevator() {
		assertNotNull(elevator);
		
		elevator.setLoaded(true);
		assertTrue(elevator.getLoaded());
	}
	
	@Test
	void test_checkDirection_firstCommand_GOINGUP_and_secondCommand_GOINGUP_returns_true() {
		assertNotNull(elevator);
		
		assertTrue(elevator.checkDirection(1, 5, 2, 3));
	}
	
	@Test
	void test_checkDirection_firstCommand_GOINGDOWN_and_secondCommand_GOINGDOWN_returns_true() {
		assertNotNull(elevator);
		
		assertTrue(elevator.checkDirection(5, 1, 3, 2));
	}
	
	@Test
	void test_checkDirection_firstCommandGOINGUP_and_secondCommand_GOINGDOWN_returns_false() {
		assertNotNull(elevator);
		
		assertFalse(elevator.checkDirection(1, 5, 3, 2));
	}
	
	@Test
	void test_removeDuplicates_method_returns_list_with_no_duplicates() {
		assertNotNull(elevator);
		
		ArrayList<Integer> duplicates = new ArrayList<Integer>();
		duplicates.add(1);
		duplicates.add(1);
		duplicates.add(2);
		duplicates.add(3);
		duplicates.add(2);

		ArrayList<Integer> noDuplicates = new ArrayList<Integer>();
		noDuplicates.add(1);
		noDuplicates.add(2);
		noDuplicates.add(3);
		
		assertEquals(noDuplicates, elevator.removeDuplicates(duplicates));
	}
	
	@Test
	void test_Elevator_thread_starts() {
		Thread eThread = new Thread(elevator);
		eThread.start();
		
		assertEquals(true, eThread.isAlive());
	}
	
	@Test
	void test_Elevator_goes_up_to_floor_3() {
		Thread eThread = new Thread(elevator);
		eThread.start();
		
		Elevator.setTravelTime(10);
		Elevator.setDoorTime(10);
		elevator.setCurrentFloor(1);
		elevator.addElevatorCommands("1:3");
		elevator.setLoaded(false);
		elevator.setUpdate(true);
		elevator.setCommand("move");
		processTime();
		elevator.setCommand("exit");

		assertEquals(3, elevator.getCurrentFloor());
	}
	
	@Test
	void test_Elevator_goes_down_to_floor_3() {
		Thread eThread = new Thread(elevator);
		eThread.start();
		
		Elevator.setTravelTime(10);
		Elevator.setDoorTime(10);
		elevator.setCurrentFloor(8);
		elevator.addElevatorCommands("7:3");
		elevator.setLoaded(false);
		elevator.setUpdate(true);
		elevator.setCommand("move");
		processTime();
		elevator.setCommand("exit");

		assertEquals(3, elevator.getCurrentFloor());
	}
	
	@Test
	void test_Elevator_goes_up_to_floor_5_with_two_commands() {
		Thread eThread = new Thread(elevator);
		eThread.start();
		
		Elevator.setTravelTime(10);
		Elevator.setDoorTime(10);
		elevator.setCurrentFloor(1);
		elevator.addElevatorCommands("1:3");
		elevator.addElevatorCommands("2:5");
		elevator.setLoaded(false);
		elevator.setUpdate(true);
		elevator.setCommand("move");
		processTime();
		elevator.setCommand("exit");

		assertEquals(5, elevator.getCurrentFloor());
	}
	
	@Test
	void test_Elevator_goes_down_to_floor_5_with_two_commands() {
		Thread eThread = new Thread(elevator);
		eThread.start();
		
		Elevator.setTravelTime(10);
		Elevator.setDoorTime(10);
		elevator.setCurrentFloor(10);
		elevator.addElevatorCommands("9:5");
		elevator.addElevatorCommands("8:6");
		elevator.setLoaded(false);
		elevator.setUpdate(true);
		elevator.setCommand("move");
		processTime();
		elevator.setCommand("exit");

		assertEquals(5, elevator.getCurrentFloor());
	}
	
	@Test
	void test_Elevator_with_two_commands_going_opposite_direction_end_currentFloor_at_5() {
		Thread eThread = new Thread(elevator);
		eThread.start();
		
		Elevator.setTravelTime(10);
		Elevator.setDoorTime(10);
		elevator.setCurrentFloor(1);
		elevator.addElevatorCommands("1:7");
		elevator.addElevatorCommands("10:5");
		elevator.setLoaded(false);
		elevator.setUpdate(true);
		elevator.setCommand("move");
		processTime();
		elevator.setCommand("exit");

		assertEquals(5, elevator.getCurrentFloor());
	}
	
	@Test
	void test_Elevator_with_4_commands_going_opposite_direction_end_currentFloor_at_5() {
		Thread eThread = new Thread(elevator);
		eThread.start();
		
		Elevator.setTravelTime(10);
		Elevator.setDoorTime(10);
		elevator.setCurrentFloor(1);
		elevator.addElevatorCommands("1:7");
		elevator.addElevatorCommands("10:8");
		elevator.addElevatorCommands("3:6");
		elevator.addElevatorCommands("9:5");
		elevator.setLoaded(false);
		elevator.setUpdate(true);
		elevator.setCommand("move");
		processTime();
		elevator.setCommand("exit");

		assertEquals(5, elevator.getCurrentFloor());
	}
	
	@Test
	void test_elevator_thread_exits() {
		Thread eThread = new Thread(elevator);
		eThread.start();
		elevator.setCommand("exit");
		processTime();
		assertFalse(eThread.isAlive());
	}

	private void processTime() {
		
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
	}
}	