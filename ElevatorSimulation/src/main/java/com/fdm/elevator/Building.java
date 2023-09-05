package com.fdm.elevator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class Building implements Runnable {
	
	private static Building instance;
	private ArrayList<String> commands;
	private ArrayList<Elevator> elevators;
	private Scheduler scheduler;
	private int minFloor;
	private int maxFloor;
	
	/**
	 * Constructor for Building
	 * 
	 * @param commands		Array of commands to read from
	 * @param elevators		Array of elevators to run
	 * @param scheduler		Reference to scheduler object	
	 * @param minFLoor		Lowest floor number of the building
	 * @param maxFloor		Highest floor number of the building
	 */
	private Building(ArrayList<String> commands, ArrayList<Elevator> elevators, Scheduler scheduler, int minFLoor, int maxFloor) {
		this.commands = commands;
		this.elevators = elevators;
		this.scheduler = scheduler;
		this.minFloor = minFLoor;
		this.maxFloor = maxFloor;
	}
	
    public static Building getInstance(ArrayList<String> commands, ArrayList<Elevator> elevators, Scheduler scheduler, int minFloor, int maxFloor) {
        if (instance == null) {
            instance = new Building(commands, elevators, scheduler, minFloor, maxFloor);
        }
        return instance;
    }
    
    public static Building getInstance() {
        return instance;
    }
	
	public int getMinFloor() {
		return minFloor;
	}

	public void setMinFloor(int minFloor) {
		this.minFloor = minFloor;
	}

	public int getMaxFloor() {
		return maxFloor;
	}

	public void setMaxFloor(int maxFloor) {
		this.maxFloor = maxFloor;
	}
	
	public ArrayList<String> getCommands() {
		return commands;
	}

	public void setCommands(ArrayList<String> commands) {
		this.commands = commands;
	}
	
	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	public List<Elevator> getElevators(){
		return elevators;
	}
	
	/**
	 * Continuously reads each command from the commands list and uses the scheduler to 
	 * choose the best elevator for the command. Command is then added to the Elevator's
	 * commands array for the elevator to process then update the elevator and set the
	 * command.
	 */
	@Override
	public void run() {
		boolean running = true;
		String command;
		Elevator elevator;
		
		while (running) { 
			try {
				
			if (commands.contains("exit")) {
				running = false;
				System.out.println("EXITING BUILDING");
				for (Elevator e : elevators) {
					e.setCommand("exit");
				}
				break;
			}
			if (commands.size() > 0) {
				command = commands.get(0);
				elevator = scheduler.chooseElevator(command);
				if (elevator.getLoaded())
					elevator.setLoaded(false);
				elevator.addElevatorCommands(command);
				elevator.setUpdate(true);
				elevator.setCommand("move");
				commands.remove(0);
			}
				Thread.sleep(100); // Let other threads do something
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Saves the main commands array, all elevator attributes, minimum floor
	 * and maximum floors of the building to a JSON file.
	 * 
	 * @param fileName	name of the file to write to
	 */
	public void saveStateToJson(String fileName) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(this);
		
		try (FileWriter fileWriter = new FileWriter(fileName)) {
			fileWriter.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
