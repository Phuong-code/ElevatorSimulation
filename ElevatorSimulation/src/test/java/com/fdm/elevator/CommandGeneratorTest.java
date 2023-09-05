package com.fdm.elevator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

public class CommandGeneratorTest {

    private int maxFloor;
    private int maxRequestPerCommand;
    private int generatetimeIterval;
    private int mode;
    private ArrayList<String> commands;
    private boolean running = true;
    ElevatorLogging log = new ElevatorLogging();
    Random random = new Random();
    
    @BeforeEach
    public void setup() {

        commands = new ArrayList<>();
        maxFloor = 10;
        maxRequestPerCommand = 2;
        generatetimeIterval = 2000;
        
    }
    
	@Test
	public void test_command_generator_mode_0_generator_off() {
		int requestPerCommand = 2;
		mode = 0;
		CommandGenerator generator = new CommandGenerator(commands, maxFloor, mode, generatetimeIterval, maxRequestPerCommand);
		
		generator.generateCommand(requestPerCommand);
		assertEquals(0, commands.size());
	}
	
	@Test
	public void test_command_generator_mode_1_morning_rush_check_number_of_commands_created() {
		int requestPerCommand = 2;
		mode = 1;
		CommandGenerator generator = new CommandGenerator(commands, maxFloor, mode, generatetimeIterval, maxRequestPerCommand);
		
		generator.generateCommand(requestPerCommand);
		assertEquals(requestPerCommand, commands.size());
	}
	
	@Test
	public void test_command_generator_mode_1_morning_rush_check_source_level_is_1() {
		int requestPerCommand = 1;
		mode = 1;
		CommandGenerator generator = new CommandGenerator(commands, maxFloor, mode, generatetimeIterval, maxRequestPerCommand);
		
		generator.generateCommand(requestPerCommand);
		assertEquals(1, Integer.parseInt(commands.get(0).split(":")[0]));
	}
	
	@Test
	public void test_command_generator_mode_2_morning_rush_check_number_of_commands_created() {
		int requestPerCommand = 2;
		mode = 2;
		CommandGenerator generator = new CommandGenerator(commands, maxFloor, mode, generatetimeIterval, maxRequestPerCommand);
		
		generator.generateCommand(requestPerCommand);
		assertEquals(requestPerCommand, commands.size());
	}
	
	@Test
	public void test_command_generator_mode_2_end_of_day_check_destination_level_is_1() {
		int requestPerCommand = 1;
		mode = 2;
		CommandGenerator generator = new CommandGenerator(commands, maxFloor, mode, generatetimeIterval, maxRequestPerCommand);
		
		generator.generateCommand(requestPerCommand);
		
		assertEquals(1, Integer.parseInt(commands.get(0).split(":")[1]));
	}
	
	@Test
	public void test_command_generator_mode_3_random_mode_check_number_of_commands_created() {
		int requestPerCommand = 2;
		mode = 3;
		CommandGenerator generator = new CommandGenerator(commands, maxFloor, mode, generatetimeIterval, maxRequestPerCommand);
		
		generator.generateCommand(requestPerCommand);
		assertEquals(requestPerCommand, commands.size());
	}
	

	@Test
	public void test_mode_setter() {
		CommandGenerator generator = new CommandGenerator(commands, maxFloor, 0, generatetimeIterval, maxRequestPerCommand);
		generator.setMode(1);
		assertEquals(1, generator.getMode());
	}
	
	// test if thread is alive
	@Test
	public void run_test_thread_is_alive() {
		
		CommandGenerator generator = new CommandGenerator(commands, maxFloor, mode, generatetimeIterval, maxRequestPerCommand);
        Thread generatorThread= new Thread(generator);
        
        generatorThread.start();        
        assertEquals(true, generatorThread.isAlive());
	}
	
	@Test
	public void run_test_thread_is_interrupt_and_exception_is_throwed() {
		
		CommandGenerator generator = new CommandGenerator(commands, maxFloor, mode, generatetimeIterval, maxRequestPerCommand);
        Thread generatorThread= new Thread(generator);
        
        generatorThread.start();
        generatorThread.interrupt();
        
        assertEquals(true, generatorThread.isAlive());
	}
}
