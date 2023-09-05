package com.fdm.elevator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.LinkedHashSet;

public class Elevator implements Runnable {

	private int id;
	private int currentFloor;
	private ElevatorState currentState = ElevatorState.STATIONARY; // Elevator begins on ground floor
	private String command;				// Current message to process
	private boolean running = true;
	private static int TRAVELTIME = 1000; // milliseconds
	private static int DOORTIME = 1000; // milliseconds
	ElevatorLogging log = new ElevatorLogging();
	private ArrayList<String> elevatorCommands = new ArrayList<String>(); // Commands to process through
	private ArrayList<Integer> floorStops = new ArrayList<Integer>(); // Floors to stop and open door
	private boolean update;		// Check to see if new command has been added
	private boolean loaded = false;		// Check to see if state loaded from JSON
	
	/**
	 * Constructor for Elevator
	 * 
	 * @param 	id ID set for Elevator
	 */
    public Elevator(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
    public int getCurrentFloor() {
    	return currentFloor;
    }
    
    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

	public ElevatorState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(ElevatorState currentState) {
		this.currentState = currentState;
	}

	public static int getTraveltime() {
		return TRAVELTIME;
	}

	public static int getDoortime() {
		return DOORTIME;
	}

	public String getCommand() {
		return command;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	

	public ArrayList<String> getElevatorCommands() {
		return elevatorCommands;
	}

	public void addElevatorCommands(String command) {
		this.elevatorCommands.add(command);
	}

	public boolean getUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update; // To check if a new command has been added
	}

	public boolean getLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	/**
	 * FOR TESTING PURPOSE ONLY
	 */
	public static void setTravelTime(int time) {
		TRAVELTIME = time;
	}
	
	public static void setDoorTime(int time) {
		DOORTIME = time;
	}
	
	/**
	 * Continuously process the commands from its command array and does the following
	 * to decide where the elevator will go.
	 * 
	 * 1. Adds other commands to the stopsFloor array if they are going the same direction
	 * as the first command and remove it from the elevator's commands array.
	 * 2. Sort the stopsFloor array according to the direction the elevator is moving at.
	 * Up is ascending and Down is descending order.
	 * 3. Move the elevator in accordance with the stopsFloor array. The first index is the
	 * destination.
	 * 4. Process the next command and repeat
	 */
	@Override
	public void run() {
		int source;
		int destination;
		int secondSource;
		int secondDestination;
		
		while (running == true ) {			// process commands queue for messages

			try {
				if (command != null) {
					if (command.equals("exit")) {
						running = false;
						System.out.println(String.format("Elevator %1$d exit", id));
						break;
					}
					while (update) {
						// Only run if commands are added from Building 
						if (elevatorCommands.size() > 0 && loaded == false) {
						
							source = Integer.parseInt(elevatorCommands.get(0).split(":")[0]);
							destination = Integer.parseInt(elevatorCommands.get(0).split(":")[1]);
							update = false;
							if(elevatorCommands.size() > 1) {

								floorStops.add(source);			// Adds the source part of the command to the floorStops list
								floorStops.add(destination);	// Adds the destination part of the command to the floorStops list
								
								// Check if the direction of first command is the same as the second command
								for (int i = 1; i<elevatorCommands.size(); i++) {
									secondSource = Integer.parseInt(elevatorCommands.get(i).split(":")[0]);
									secondDestination = Integer.parseInt(elevatorCommands.get(i).split(":")[1]);
									
									// This command is going the same direction as the first command
									if (checkDirection(source, destination, secondSource, secondDestination)) {
										floorStops.add(secondSource);		// Adds the source part of the command to the floorStops list
										floorStops.add(secondDestination);	// Adds the part part of the command to the floorStops list
										elevatorCommands.remove(i);
										i-=1; // adjust for the removal of command from array
									}									
								}
							}
							else if (elevatorCommands.size() == 1) {
								source = Integer.parseInt(elevatorCommands.get(0).split(":")[0]);
								destination = Integer.parseInt(elevatorCommands.get(0).split(":")[1]);
								floorStops.add(source);			// Adds the source part of the command to the floorStops list
								floorStops.add(destination);	// Adds the part part of the command to the floorStops list
							}

							Thread.sleep(100); // Let other threads do something
							
							// Check if a new command has been added
							if (update) {
								break;
							}
							
							// No extra commands therefore remove initial command from array 
							elevatorCommands.remove(0);
							floorStops = removeDuplicates(floorStops);
							if (source < destination) {
								floorStops.sort(null);	// Elevator going up then sort in ascending								
							}
							else {
								floorStops.sort(Collections.reverseOrder()); // Elevator going down then sort in descending
							}
							
						} // if (elevatorCommands > 0)
						
						// First index the array is the destination the elevator is going to
						// Finishes previous saved floors if loaded from JSON as well
						while (floorStops.size() > 0) {
							// Elevator stop to open/close door
							if (currentFloor == floorStops.get(0)) {
								currentState = ElevatorState.OPENCLOSE;
								command = "OPENCLOSE";
								log.returnCurrentLevel(id,currentFloor);
								Thread.sleep(DOORTIME);
								floorStops.remove(0);
							}
							// Elevator going UP
							else if (currentFloor < floorStops.get(0)) {
								currentState = ElevatorState.UP;
								command = "UP";
								log.returnCurrentLevel(id,currentFloor);
								Thread.sleep(TRAVELTIME);
								currentFloor++;
							}
							// Elevator going DOWN
							else if (currentFloor > floorStops.get(0)) {
								currentState = ElevatorState.DOWN;
								command = "DOWN";
								log.returnCurrentLevel(id,currentFloor);
								Thread.sleep(TRAVELTIME);
								currentFloor--;
							}
						}

						// Elevator fulfilled all commands from floorStops array
						currentState = ElevatorState.STATIONARY;
						
						// Continue moving if there are still commands to execute
						if (elevatorCommands.size() > 0) {
							command = "move";
							update = true;
							loaded = false;
						}
						else {
							command = null;							
						}
							
					} // while (update) loop
					
				} // if (command != null)

				Thread.sleep(100);	// Let other threads do something
	
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
				
		} // while(running)
	} // run()
	
	
	/**
	 * Checks if the first command is going the same direction as the second command
	 * 
	 * @param 	src1	source floor of the first command
	 * @param 	dest1	destination floor of the first command
	 * @param 	src2	source floor of the second command
	 * @param 	dest2	destination floor of the second command
	 * @return	boolean true if same direction else false
	 */
	public boolean checkDirection(int src1, int dest1, int src2, int dest2) {
		
		if (src1 < dest1 && src2 < dest2 || src1 > dest1 && src2 > dest2)  // Both commands are going the same direction
			return true;
		else
			return false;
	}
	
	/**
	 * Adds elements of the ArrayList into a LinkedHashSet
	 * Utilises LinkedHashSet properties (doesn't allow duplicates and is ordered)
	 * to create an ArrayList of elements that doesn't contain any duplicate elements 
	 * 
	 * @param 	<T>		Generic type of the ArrayList
	 * @param 	list	ArrayList with duplicate elements
	 * @return	An ArrayList with duplicate elements
	 */
	 // Function to remove duplicates from an ArrayList
    public <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {
  
        // Create a new LinkedHashSet
        Set<T> set = new LinkedHashSet<>();
  
        // Add the elements to set
        set.addAll(list);
  
        // Clear the list
        list.clear();
  
        // add the elements of set
        // with no duplicates to the list
        list.addAll(set);
  
        // return the list
        return list;
    }
}
