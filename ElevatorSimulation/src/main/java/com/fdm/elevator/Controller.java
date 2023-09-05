/**
 * Controller is the main runner for elevator project. It reads environment settings 
 * from a configuration file, starts threads and initialises 
 */
package com.fdm.elevator;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Properties;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Controller {
	private static final String CONFIG_FILE_PATH = "src/main/resources/configuration.txt";
	private static final String JSON_FILE_PATH = "src/main/resources/building_state.json";
	
    private ArrayList<String> commands;
    private ArrayList<Elevator> elevators;
    private int minFloor;
    private int maxFloor;
    
	public static void main(String[] args) {
        // Create an instance of Controller
        Controller controller = new Controller();
        
        // Prompt the user to load state from JSON file
        boolean loadState = controller.promptUserForLoadState();
        
        if (loadState) {
            // Load state from JSON file
            controller.loadStateFromJson(JSON_FILE_PATH);
        } else {
            // Load state from configuration
            controller.loadStateFromConfiguration();
        }
	}
	
	protected void loadStateFromJson(String fileName) {
        Properties properties = readConfigurationFile();
        
        int maxRequestPerCommand = Integer.parseInt(properties.getProperty("maxRequestPerCommand"));
        int mode = Integer.parseInt(properties.getProperty("startingMode"));

        try (FileReader fileReader = new FileReader(fileName)) {
            Gson gson = new GsonBuilder().create();
            Building savedBuilding = gson.fromJson(fileReader, Building.class);

            this.commands = savedBuilding.getCommands();
            this.elevators = (ArrayList<Elevator>) savedBuilding.getElevators();
//            this.scheduler = savedBuilding.getScheduler();
            this.minFloor = savedBuilding.getMinFloor();
            this.maxFloor = savedBuilding.getMaxFloor();
            
            // Perform any additional setup or state restoration

            CommandGenerator generator = new CommandGenerator(this.commands, this.maxFloor, mode, 2000, maxRequestPerCommand);
            Thread generatorThread = new Thread(generator);
            generatorThread.start();

            Thread inputThread = new Thread(new CommandThread(this.commands, generator, this.maxFloor));
            inputThread.start();
 
            // Start the elevator threads
            for (Elevator elevator : this.elevators) {
            	elevator.setUpdate(true);
            	elevator.setLoaded(true);
                Thread elevatorThread = new Thread(elevator);
                elevatorThread.start();
            }
   
            Scheduler newscheScheduler = new Scheduler(this.elevators);

            Building building = Building.getInstance(this.commands, this.elevators, newscheScheduler, this.minFloor, this.maxFloor);
            Thread buildingThread = new Thread(building);
            buildingThread.start();

            // Implementing FrameView
            FrameView frameView = new FrameView(this.minFloor, this.maxFloor, this.elevators.size(), this.elevators);
            Thread frameViewThread = new Thread(frameView);
            frameViewThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    protected void loadStateFromConfiguration() {
		Properties properties = readConfigurationFile();
		
		//Read settings from configuration file 
		int minFloor = Integer.parseInt(properties.getProperty("minFloor"));
        int maxFloor = Integer.parseInt(properties.getProperty("maxFloor"));
        int numberOfElevators = Integer.parseInt(properties.getProperty("numberOfElevator"));
        int maxRequestPerCommand = Integer.parseInt(properties.getProperty("maxRequestPerCommand"));
        int mode = Integer.parseInt(properties.getProperty("startingMode"));
        
        // Initialise ArrayList elevators and commands
        ArrayList<Elevator> elevators;
		ArrayList<String> commands = new ArrayList<String>();
		
		elevators = initialiseElevators(numberOfElevators);
		Scheduler scheduler = new Scheduler(elevators);

		// Start all threads
        CommandGenerator generator = new CommandGenerator(commands,maxFloor, mode, 2000, maxRequestPerCommand);
        Thread generatorThread= new Thread(generator);
        generatorThread.start();
        
		Thread inputThread = new Thread(new CommandThread(commands,generator,maxFloor));
		inputThread.start();

		Building building = Building.getInstance(commands, elevators, scheduler, minFloor, maxFloor);
		Thread buildingThread = new Thread(building);
		buildingThread.start();
		
		// Implementing FramView
		FrameView frameView = new FrameView(building.getMinFloor(), building.getMaxFloor() , numberOfElevators, elevators);
		Thread frameViewThread = new Thread(frameView);
		frameViewThread.start();
    }
	
	/**
	 * Method readConfigurationFile
	 * 		  This method reads from the configuration file and return it as String.
	 * @return String
	 */
    private static Properties readConfigurationFile() {
        Properties properties = new Properties();

        try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE_PATH))) {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

    /**
     * Method initialiseElevators
     * 		  This method initialises Elevator objects, adds them into ArrayList then return the list.
     * @param noOfElevators number of elevators
     * @return ArrayList of Elevator object
     */
	public static ArrayList<Elevator> initialiseElevators(int noOfElevators){
		ArrayList<Elevator> elevators = new ArrayList<Elevator>();
		
		for(int i=0; i<noOfElevators; i++){
			Elevator elevator = new Elevator(i+1);
			elevator.setCurrentFloor(1);
			Thread thread = new Thread(elevator);
			thread.start();
			elevators.add(elevator);
		}
		
		return elevators;
	}
	
	public boolean promptUserForLoadState() {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to load the state from a JSON file? (y/n): ");
        String input = scanner.nextLine().trim().toLowerCase();
        
        return input.equals("y") || input.equals("yes");
    }
	
}
