package com.fdm.elevator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockitoAnnotations;

@ExtendWith(MockitoExtension.class)
public class BuildingTest {
    private Building building;
    private ArrayList<String> commands;
    private ArrayList<Elevator> elevators;
    Elevator elevator1;
    Elevator elevator2;
    Elevator elevator3;
    private Scheduler scheduler;
    
    @Mock
    private Building building2;
    
    @Mock
    private ArrayList<String> commandsMock;
    @Mock
    private ArrayList<Elevator> elevatorsMock;
    
    @Mock
    private Elevator elevatorMock;
    
    @Mock
    private Scheduler schedulerMock;

    @BeforeEach
    public void setup() {
    	MockitoAnnotations.openMocks(this);
        commands = new ArrayList<>(Arrays.asList("1:3", "2:5", "4:2"));
        elevator1 = new Elevator(1);
        elevator2 = new Elevator(2);
        elevator3 = new Elevator(3);
        elevators = new ArrayList<>();
        elevators.add(elevator1);
        elevators.add(elevator2);
        elevators.add(elevator3);
        scheduler = new Scheduler(elevators);
        int minFloor = 1;
        int maxFloor = 10;
            
    }
    
    @Test
    public void TestBuidlingThreadIsAlive() {
    	Building building = Building.getInstance(commands, elevators, scheduler, 1, 10);
    	Thread thread = new Thread(building);
    	thread.start();
    	assertTrue(thread.isAlive());
    	commands.add("exit");
    }
    
    @Test
    public void TestRun_normal() {
    	
        // Arrange
    	commandsMock.add("1:2");
    	building2 = Building.getInstance(commandsMock, elevatorsMock, schedulerMock, 1, 10);
//    	when(schedulerMock.chooseElevator("1:2")).thenReturn(elevatorMock);
        // Act
     
        Thread thread = new Thread(building);
        thread.start();

        commandsMock.add("exit");
        // Assert
        assertFalse(commandsMock.isEmpty());
    }
    
    @Test
    public void testRun_ExitCommand_ShouldStopBuildingAndSetElevatorCommands() {
        // Arrange
    	commandsMock.add("exit");
    	
        // Act
        building2 = Building.getInstance(commandsMock, elevatorsMock, schedulerMock, 1, 10);
        Thread thread = new Thread(building2);
        thread.start();

        
        // Assert
	    verify(schedulerMock, never()).chooseElevator(anyString());
    }


    
    @Test
    public void testSaveStateToJson() {
    	Building building = Building.getInstance(commands, elevators, scheduler, 1, 10);
        // Set up test data or modify the state of the instance if needed
        
        // Set the file name for the JSON output
        String fileName = "testOutput.json";
        
        // Call the method to be tested
        building.saveStateToJson(fileName);
        
        File file = new File(fileName);
        
        // Verify that the file was created
        assertTrue(file.exists());
        
    }
    
    @Test
    public void getInstance() {
    	Building building = Building.getInstance(commands, elevators, scheduler, 1, 10);
    	Building getInstanceBuilding = Building.getInstance();
    	Assertions.assertEquals(building, getInstanceBuilding);
    }

    @Test
    public void getMinFloorTest() {
    	Building building = Building.getInstance(commands, elevators, scheduler, 1, 10);
        int minFloor = building.getMinFloor();
        Assertions.assertEquals(1, minFloor);
    }

    @Test
    public void setMinFloorTest() {
    	Building building = Building.getInstance(commands, elevators, scheduler, 1, 10);
        building.setMinFloor(5);
        int minFloor = building.getMinFloor();
        Assertions.assertEquals(5, minFloor);
    }

    @Test
    public void getMaxFloorTest() {
    	Building building = Building.getInstance(commands, elevators, scheduler, 1, 10);
        int maxFloor = building.getMaxFloor();
        Assertions.assertEquals(10, maxFloor);
    }

    @Test
    public void setMaxFloorTest() {
    	Building building = Building.getInstance(commands, elevators, scheduler, 1, 10);
        building.setMaxFloor(15);
        int maxFloor = building.getMaxFloor();
        Assertions.assertEquals(15, maxFloor);
    }

    @Test
    public void getCommandsTest() {
    	Building building = Building.getInstance(commands, elevators, scheduler, 1, 10);
        List<String> commandsList = building.getCommands();
        Assertions.assertEquals(commands, commandsList);
    }

    @Test
    public void setCommandsTest() {
    	Building building = Building.getInstance(commands, elevators, scheduler, 1, 10);
        ArrayList<String> newCommands = new ArrayList<>(Arrays.asList("3:7", "6:9"));
        building.setCommands(newCommands);
        List<String> commandsList = building.getCommands();
        Assertions.assertEquals(newCommands, commandsList);
    }

    @Test
    public void setSchedulerTest() {
    	Building building = Building.getInstance(commands, elevators, scheduler, 1, 10);
        Scheduler newScheduler = mock(Scheduler.class);
        building.setScheduler(newScheduler);
        Scheduler buildingScheduler = building.getScheduler();
        Assertions.assertEquals(newScheduler, buildingScheduler);
    }

    @Test
    public void getElevatorsTest() {
    	Building building = Building.getInstance(commands, elevators, scheduler, 1, 10);
        List<Elevator> elevatorsList = building.getElevators();
        Assertions.assertEquals(elevatorsList, elevatorsList);
    }
}
