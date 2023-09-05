package com.fdm.elevator;

import org.junit.jupiter.api.Test;

public class ElevatorLoggingTest {

	// Testing log sends to TestCommandLog.Log
	@Test
	public void testCommandLogging() {
		ElevatorLogging logging = new ElevatorLogging();
		String command = "1:3";
		logging.generatedCommandLog(command);
	}
	
	// Testing log sends to TestTraceLog.Log
	@Test
	public void testTraceLogging() {
		ElevatorLogging logging = new ElevatorLogging();
		int id = 2;
		int currentFloor = 3;
		logging.returnCurrentLevel(id, currentFloor);
	}
}
