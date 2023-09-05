/**
 * Class CommandThread adds valid command from manual input
 * to ArrayList commands for Building to process.
 */
package com.fdm.elevator;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CommandThread implements Runnable {

	private ArrayList<String> commands;
	private CommandGenerator generator;
	private int maxFloor;
	private Building building;
	private static final String JSON_FILE_PATH = "src/main/resources/building_state.json"; 
	Scanner myObj = new Scanner(System.in);  // Create a Scanner object
	
	/**
	 * Constructor
	 * 
	 * @param commands		An ArrayList to store elevator commands from both manual input and generator
	 * @param generator		CommandGenerator object
	 * @param maxFloor		max floor number
	 */
	public CommandThread(ArrayList<String> commands,CommandGenerator generator,int maxFloor) {
		super();
		this.generator = generator;
		this.commands = commands;
		this.maxFloor = maxFloor;
	}
	
	/**
	 * Method run
	 * 
	 * Executes the thread's logic. This method is automatically called when the thread is started.
	 * It creates a scanner object and takes input from user. It removes whitespace and processes input as below
	 * exit - close scanner, add "exit" to ArrayList commands so it would notify elevators to "exit" 
	 * 0 - set command generator to off
	 * 1 - set command generator to morning rush mode
	 * 2 - set command generator to end of day mode
	 * 3 - set command generator to normal daytime mode
	 * Check input by method isValidated. 
	 * If the input is valid, then add command to ArrayList commands.
	 * If the input is invalid, then display error message.
	 */
	@Override
	public void run() {
		
	    String command;

	    boolean running = true;
	    
	    while (running == true) {

	    	command = myObj.nextLine().replace(" ", "");
	    	
	    		try {
	    			processCommand(command, running);

	    	    	Thread.sleep(100);
		    						    
		    	} catch (InterruptedException e) {	
		    		e.printStackTrace();
		    	}
	    }
	    	
	   	
	}
	
	/**
	 * Method isValidated
	 * 
	 * 		  This method check if a command string is valid by following conditions.
	 * 		  The command must match the regular expression pattern.
	 * 		  Source and destination must not be same.
	 * 		  Sources and destinations must not exceed the max floor number. 
	 
	 * @param command	input other than "exit", "0", "1", "2" and  "3"
	 * @return
	 */
	public boolean isValidated(String command) {
	   	
    	Pattern pt = Pattern.compile("[1-9][0-9]*:[1-9][0-9]*(,[1-9][0-9]*[:][1-9][0-9]*)*");
    	  	
    	if (pt.matcher(command).matches() == false)
    		return false;
    	else {
    		String[] splitCommands = command.replace(",", ":").split(":");
	    	for (int i=0;i<splitCommands.length-1;i=i+2) {
	    		if (splitCommands[i].equals(splitCommands[i+1]) || Integer.parseInt(splitCommands[i])  > maxFloor || Integer.parseInt(splitCommands[i+1]) > maxFloor)
	    			return false;    		
	    	}
	    }
	    return true;
	}
	
	/**
	 * Method processCommand
	 * 		  This method process menu commands for save, changing modes 
	 * 		  and run isValidated method
	 *        
	 * @param command String
	 * @param running boolean
	 */
	public void processCommand(String command, boolean running) {
		
		if (command.equals("exit")) {
			commands.add("exit");
			generator.setMode(0);
			running = false;
			myObj.close();
			System.out.println("EXITING");
		}
		else if (command.toLowerCase().equals("s") || command.toLowerCase().equals("save")) {
			this.building = Building.getInstance();
			building.saveStateToJson(JSON_FILE_PATH);
			System.out.println("save to building_state.json");
		}
		else if (command.equals("0")){
			generator.setMode(0);
			System.out.println("Generator off");	
		}
		else if (command.equals("1")) {
			generator.setMode(1);
			System.out.println("Simulating morning rush");		    			
		}
		else if (command.equals("2")) {
			generator.setMode(2);
			System.out.println("Simulating end of day rush");		    			
		}
		else if (command.equals("3")) {
			generator.setMode(3);
			System.out.println("Simulating normal daytime");	    			
		}
		else if (isValidated(command) == true) {
    		
			String[] splitCommands;
	    	splitCommands = command.split(",");
	    	for (String c : splitCommands) {
	    		commands.add(c);
	    	}
	    }			    		
	    else{	    		    		
	    	System.out.println("Invalid command!");
	    }
	}

}
