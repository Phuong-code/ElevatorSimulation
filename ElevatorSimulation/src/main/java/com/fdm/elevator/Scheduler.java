/**
 * Class Scheduler takes a command and choose a suitable elevator to run.
 */
package com.fdm.elevator;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Scheduler {
	private List<Elevator> elevators;
	
	/**
	 * Constructor
	 * @param elevators A List of Elevator objects.
	 */
	public Scheduler(List<Elevator> elevators) {
		this.elevators = elevators;
	}


	/**
	 * Method chooseElevator
	 * 		  This method takes a command of request and chooses the most suitable elevator to run.
	 * 		  For elevators are moving in the same direction as the request, and haven't passed
	 * 		  the source level, the closest elevator to the source level will be chosen.
	 * 		  Otherwise a stationary elevator will be chosen if there is one.
	 * 		  
	 *  
	 * @param command	The command is being processed
	 * @return
	 */
	public Elevator chooseElevator(String command) {
	    // Parse the command to extract the source and destination floors
	    String[] floors = command.split(":");
	    int sourceFloor = Integer.parseInt(floors[0]);
	    int destinationFloor = Integer.parseInt(floors[1]);
	    ElevatorState directionRequest = ElevatorState.STATIONARY;
	    
	    //get request direction
	    if ((destinationFloor - sourceFloor) > 0) {
	    	directionRequest = ElevatorState.UP;
	    } else {
	    	directionRequest = ElevatorState.DOWN;
	    }

	    // Iterate through the elevators to find the most suitable one
	    Elevator chosenElevator = null;
	    int minDistance = Integer.MAX_VALUE;
	    
	    // request direction is going up
    	if (directionRequest == ElevatorState.UP) {
		    for (Elevator elevator : elevators) {
		        // Check if the elevator is running in the same direction as the command
		        if ((elevator.getCurrentFloor() < sourceFloor && elevator.getCurrentState() == ElevatorState.UP)) {
		            int distance = Math.abs(elevator.getCurrentFloor() - sourceFloor);
		            if (distance < minDistance) {
		                minDistance = distance;
		                chosenElevator = elevator;
		            }
		        }
		    }
		 // request direction is going down
    	} else if (directionRequest == ElevatorState.DOWN) {
		    for (Elevator elevator : elevators) {
		        // Check if the elevator is running in the same direction as the command
		        if ((elevator.getCurrentFloor() > sourceFloor && elevator.getCurrentState() == ElevatorState.DOWN)) {
		            int distance = Math.abs(elevator.getCurrentFloor() - sourceFloor);
		            if (distance < minDistance) {
		                minDistance = distance;
		                chosenElevator = elevator;
		            }
		        }
		    }
    	}

	    // If a suitable elevator is not found, pick the closest stationary elevator
	    if (chosenElevator == null) {
	    	for (Elevator elevator : elevators) {
	    		if (elevator.getCurrentState() == ElevatorState.STATIONARY) {
		            int distance = Math.abs(elevator.getCurrentFloor() - sourceFloor);
		            if (distance < minDistance) {
		                minDistance = distance;
		                chosenElevator = elevator;
		            }
	    		}
	    	}  
	    } 
	    // If none of the condition is met, pick a random elevator
	    if (chosenElevator == null) {
	        Random random = new Random();
	    	chosenElevator = elevators.get(random.nextInt(elevators.size()));
	    }
	    
	    return chosenElevator;
	}

}
