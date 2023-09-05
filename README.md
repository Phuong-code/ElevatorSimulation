# ElevatorSimulation

## Instructions

To run a clean start
    - Run the program from the controller
    - To have a clean start, after the program prompts to load from a JSON file then type n, no or hit enter


## Configurations

You can change the configuration of the elevator program in the configuration file under src/main/resource.

Configurations are:
1. minFloor (minimum of 1)
2. maxFloor
3. numberOfElevator
4. maxRequestPerCommand
5. startingMode (Mode to set the auto command generator. Initially it is 0 therefore it's doing nothing)

## Auto Command Generater Mode

Type in any of these commands in the command line to being using the auto generator.

0 - Turn auto generator off <br />
1 - Simulate morning rush (Fixed elevator source at floor 1) <br />
2 - Simulate end of day (Fixed elevator destination at floor 1) <br />
3 - Simulate normal day (Randomize commands within the floor ranges in the configuration file) <br />

## JSON

Type save or s at anytime to save the current state of the Building
Rerun the program and when prompted to load from a JSON file then type y or yes
