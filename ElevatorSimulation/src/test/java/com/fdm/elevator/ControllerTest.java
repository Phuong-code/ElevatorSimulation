package com.fdm.elevator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Scanner;

public class ControllerTest {
    private Controller controller;
    private ByteArrayInputStream testIn;
    
    
    @BeforeEach
    void setup() {
        controller = new Controller();
    }

    @Test
    void testPromptUserForLoadState_Yes() {
        // Simulate user input "y"
        provideInput("y");

        assertTrue(controller.promptUserForLoadState());
    }

    @Test
    void testPromptUserForLoadState_No() {
        // Simulate user input "n"
        provideInput("n");

        assertFalse(controller.promptUserForLoadState());
    }

    @Test
    void testInitialiseElevators_ValidNumberOfElevators_ReturnsElevatorList() {
        // Set up
        int numberOfElevators = 2;

        // Execute
        ArrayList<Elevator> elevators = Controller.initialiseElevators(numberOfElevators);

        // Verify
        Assertions.assertEquals(numberOfElevators, elevators.size());
        for (int i = 0; i < numberOfElevators; i++) {
            Elevator elevator = elevators.get(i);
            Assertions.assertEquals(i + 1, elevator.getId());
            Assertions.assertEquals(1, elevator.getCurrentFloor());
        }
    }
    
    @Test
    void test_controller_load_state_from_configuration() {
    	controller.loadStateFromConfiguration();
    }
    
    @Test
    void test_controller_load_state_from_JSON() {
    	controller.loadStateFromJson("building_state.json");
    }

    @Test
    void test_user_input_no_fresh_clean_start() {
    	provideInput("n");
    	Controller.main(null);
    	provideInput("exit");
    }
    
    @Test
    void test_user_input_yes_load_from_JSON() {
    	provideInput("yes");
    	Controller.main(null);
    	provideInput("exit");
    }
    
    
    @Test
    void test_user_input_command() {
    	provideInput("yes");
    	Controller.main(null);
    	provideInput("1:3");
    	processTime();
    	provideInput("exit");
    }
    
    private void provideInput(String data) {
    	testIn = new ByteArrayInputStream(data.getBytes());
    	System.setIn(testIn);
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

