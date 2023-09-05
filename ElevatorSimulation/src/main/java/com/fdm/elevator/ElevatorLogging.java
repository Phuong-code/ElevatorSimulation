/**
 * Class ElevatorLogging store and display movement and command logs
 */
package com.fdm.elevator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ElevatorLogging {

	private static final Logger LOGGER = LogManager.getLogger(ElevatorLogging.class);
	
	/**
	 * Method returnCurrentLevel
	 * 		  This method Logs elevator movements and stores them in TraceLog.Log
	 * @param id					Elevator ID
	 * @param currentFloor			Elevator's current floor
	 */
	public void returnCurrentLevel(int id, int currentFloor){
				
		LOGGER.trace("Elevator "+id+" is at level " + currentFloor);

	}

	/**
	 * Method generatedCommandLog
	 * 		  This method Logs commands generated by command generator,
	 * 		  stores them in CommandLog.Log and displays in the console
	 * @param command
	 */
	public void generatedCommandLog(String command){
		
		LOGGER.info(command);

	}
}
