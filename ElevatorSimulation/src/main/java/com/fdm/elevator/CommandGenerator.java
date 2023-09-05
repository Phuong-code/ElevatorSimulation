/**
 * Class CommandGenerator generates commands and adds them to ArrayList commands for Class building to process
 */
package com.fdm.elevator;

import java.util.ArrayList;      
import java.util.Random;

public class CommandGenerator implements Runnable {
	 
    private int maxFloor;
    private int maxRequestPerCommand;
    private int generatetimeIterval;
    private int mode;
    private ArrayList<String> commands;
    private boolean running = true;
    ElevatorLogging log = new ElevatorLogging();
    Random random = new Random();
    
    /**
     * Constructor
     * 
     * @param commands					An ArrayList to store elevator commands from both manual input and generator
     * @param maxFloor					max floor number
     * @param mode						Generator mode 0 - off 1 - morning rush, 2 - end of day, 3 normal daytime
     * @param generatetimeIterval       Generator time interval in millisecond
     * @param maxRequestPerCommand		Max request for each command
     */
    public CommandGenerator(ArrayList<String> commands, int maxFloor, int mode, int generatetimeIterval, int maxRequestPerCommand) {
       
    	this.commands = commands;
    	this.maxFloor = maxFloor;
        this.generatetimeIterval = generatetimeIterval;
        this.maxRequestPerCommand = maxRequestPerCommand;
        this.mode = mode;
    }
    
    /**
     * Method run
     * 
     * Executes the thread's logic.This method is automatically called when the thread is started.
     * It runs method generateCommand using a while loop, generates commands indefinitely by given time interval and mode.
     * The number of requested per command will be a random positive integer which is less than or equal to maxRequestPerCommand.
     * 
     */
    @Override
    public void run() {     
        
        
		while(running == true) {
        	
        	int requestPerCommand = random.nextInt(maxRequestPerCommand) +1;
        	
        	try {
        		
        		generateCommand(requestPerCommand);            
				Thread.sleep(generatetimeIterval);
				
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
        }
              
    }
    
    /**
     * Method generateCommand
     * 		  This method generates commands base on given request number and add to ArrayList commands
     * @param requestPerCommand 	number of request for each command
     */
    public void generateCommand(int requestPerCommand) {
    		
		StringBuilder command = new StringBuilder();
		
		if (mode!=0) {
				
			for (int i = 0; i < requestPerCommand; i++) {    
				
				int source;
				int destination;
				
				
				if (mode==1) { // if mode is 1, fixes source at level 1
		    		source = 1;
		            destination = random.nextInt(1,maxFloor) + 1;
		            command.append(source).append(":").append(destination);
				}
				else if (mode==2) { // if mode is 2, fixes destination at level 1
		    		source = random.nextInt(1,maxFloor) + 1; 
		            destination = 1;
		            command.append(source).append(":").append(destination);
				}
				else if (mode ==3){ // if mode is 3, random sources and destinations
					source = random.nextInt(maxFloor) + 1; 
					destination = random.nextInt(maxFloor) + 1;
					
		            while (destination == source) { //avoid generating same destination and source
		            	destination = random.nextInt(maxFloor) + 1;
		            }
		            command.append(source).append(":").append(destination);
				}
	            
				if (i < requestPerCommand - 1) 
	                command.append(",");	                    
			}
	    	
			log.generatedCommandLog(command.toString());
			
	    	for (String c : command.toString().split(",")) {
	    		commands.add(c);
	    	}
    	}
		//Do nothing if mode is 0
    }
    
    /**
     * Method setMode
     * 		  This method is the setter for generator mode.
     * @param mode Generator mode 0 - off 1 - morning rush, 2 - end of day, 3 normal daytime
     */
    public void setMode(int mode) {
    	this.mode = mode;
    }
    
    /**
     *Method getMode
     *		 This method is the getter for generator mode.
     * @return mode number
     */
    public int getMode() {
    	return mode;
    }
}
