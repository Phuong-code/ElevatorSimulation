package com.fdm.elevator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class CommandThreadTest {

	private CommandThread commandThread;
	private boolean running;
	private CommandGenerator generator;
	private ArrayList<String> commands;
	private int maxFloor = 9;
	Scanner myObj;
	
	@BeforeEach
	public void setup() {
		commands = new ArrayList<String>();
		generator = new CommandGenerator(commands, maxFloor, 0, 200, 3);
		commandThread = new CommandThread(commands, generator, maxFloor);
		running = true;
	}
	
	
	@Test
	void test_isValidated_Returns_false_for_emptyString() {
		
		//Arrange
		String input = "";
		
		boolean expected = false; 
		
		//Act
		boolean actual = commandThread.isValidated(input);
		
		//Assert 
		assertEquals(expected, actual);
	}
	
	@Test
	void test_isValidated_Returns_True_for_ValidInput() {
		
		//Arrange
		String input = "1:3";
		
		boolean expected = true;
		
		//Act 
		boolean actual = commandThread.isValidated(input);
		  
		//Assert
		assertEquals(expected, actual);

	}
	//building: test min, max if initialised properly
	//commandgenerator test how many times it runs
	
	@Test
	void test_isValidated_Returns_False_forInvalidInput() {
		
		//Arrange
		String input = "1,9";
		
		boolean expected = false;
		
		//Act 
		boolean actual = commandThread.isValidated(input);
		  
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test
	void test_isValidated_Returns_False_forInvalidInput_Contains_InvalidCharacters1() {
		
		//Arrange
		String input = "1@3";
		
		boolean expected = false;
		
		//Act 
		boolean actual = commandThread.isValidated(input);
		  
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test
	void test_isValidated_Returns_True_for2Inputs() {
		
		//Arrange
		String input  = "1:3,2:9";

		boolean expected = true;
		
		//Act 
		boolean actual = commandThread.isValidated(input);
		  
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test
	void test_isValidated_Returns_False_for_invalidInput_2Inputs() {
		
		//Arrange
		String input = ("1:3,4-9");

		boolean expected = false;
		
		//Act 
		boolean actual = commandThread.isValidated(input);
		  
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test
	void test_isValidated_Returns_True_for3Inputs() {
		
		//Arrange
		String input = "1:3,4:9,5:9";
		
		boolean expected = true;
		
		//Act 
		boolean actual = commandThread.isValidated(input);
		  
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test
	void test_isValidated_Returns_False_for_invalidInput_3Inputs() {
		
		//Arrange
		String input = "1:3,4-9,2*4";
		
		boolean expected = false;
		
		//Act 
		boolean actual = commandThread.isValidated(input);
		  
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test
	void test_isValidated_Returns_False_for_outBoundLevel() {
		
		//Arrange
		String input = "2:7,4:13";
		
		boolean expected = false;
		
		//Act 
		boolean actual = commandThread.isValidated(input);
		  
		//Assert
		assertEquals(expected, actual);
	}
		
	@Test
	void test_isValidated_Returns_False_for_repeatLevel() {
		
		//Arrange
		String input = "2:2,3:3";
		
		boolean expected = false;
		
		//Act 
		boolean actual = commandThread.isValidated(input);
		  
		//Assert
		assertEquals(expected, actual);
	}
	
	// test if thread is alive
	@Test
	public void run_test_thread_is_alive(){
		
		String data = "1:3";
		System.setIn(new ByteArrayInputStream(data.getBytes()));
        
        commandThread.myObj = new Scanner(System.in);
        
        Thread thread= new Thread(commandThread);
        thread.start();
        
        assertEquals(true, thread.isAlive());

		
	}
	
	@Test
	public void run_test_thread_is_interrupt_and_exception_is_throwed(){
		
		String data = "1:3";
		System.setIn(new ByteArrayInputStream(data.getBytes()));
        
        commandThread.myObj = new Scanner(System.in);
        
        Thread thread= new Thread(commandThread);
        thread.start();
        thread.interrupt();
        
        assertEquals(true, thread.isAlive());
	}
	
	// test method processCommand
	@Test
	public void processCommand_switch_to_mode_0() {
		
		String command = "0";
		commandThread.processCommand(command, running);
        
        assertEquals(0, generator.getMode());
	}


	@Test
	public void processCommand_switch_to_mode_1() {
		
		String command = "1";
		commandThread.processCommand(command, running);
        
        assertEquals(1, generator.getMode());
	}
	

	@Test
	public void processCommand_switch_to_mode_2() {
		
		String command = "2";
		commandThread.processCommand(command, running);
        
        assertEquals(2, generator.getMode());
	}
	

	@Test
	public void processCommand_switch_to_mode_3() {
		
		String command = "3";
		commandThread.processCommand(command, running);
        
        assertEquals(3, generator.getMode());
	}
	
	// 
	@Test
	public void processCommand_process_a_valid_command() {
		
		String command = "1:3";
		commandThread.processCommand(command, running);
        
        assertEquals("1:3", commands.get(0));
	}
	
	@Test
	public void processCommand_process_a_invalid_command() {
		
		String command = "1@3";
		commandThread.processCommand(command, running);
        
	}
	
	@Test
	public void processCommand_process_exit_command(){
		
		String command = "exit";
		commandThread.processCommand(command, running);
        
        assertEquals("exit", commands.get(0));
        
	}
	

}
