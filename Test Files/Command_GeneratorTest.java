// Hannah Nolan gitHub ID (hnolan2019) ucd
// The select command is tested fully in the testFile as it reads in multiple selections

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class Command_GeneratorTest {

    //Command_Generator class tests
    // Test returnTotalSteps
    // display Commands
    // updateNext
    // update command
    // checkNextDuplicate
    // processCommands
    // generate moves
    // consolidateCommands
    // joinCommands
    // check duplicate

    @org.junit.jupiter.api.Test
    void testReturnTotalSteps() {
        // Setup: Create necessary mock objects
        Random roll = new Random();
        ArrayList<Integer> rolls = new ArrayList<>();
        Dice dice = new Dice(rolls);
        Turn turn = new Turn();
        ArrayList<Pip> pips = new ArrayList<>();
        ArrayList<Pip> bar = new ArrayList<>();

        Command_Generator commandGenerator = new Command_Generator(dice, pips, turn, bar);

        // Simulate some rolls and steps
        commandGenerator.processCommands();

        // Assert that the total steps are calculated correctly
        assertEquals(0, commandGenerator.returnTotalSteps(), "Total steps should be 0 if no moves are possible.");
    }


    @Test
    void testDisplayCommands2() throws Exception {
        // Setup: Create necessary mock objects
        ArrayList<Integer> rolls = new ArrayList<>();
        rolls.add(4);
        rolls.add(5);
        Dice dice = new Dice(rolls);

        Turn turn = new Turn();
        turn.setTurn(true, true);

        ArrayList<Pip> pips = new ArrayList<>();
        ArrayList<Pip> bar = new ArrayList<>();

        Command_Generator commandGenerator = new Command_Generator(dice, pips, turn, bar);


        // Use reflection to access the private `nextMove` field
        java.lang.reflect.Field nextMoveField = Command_Generator.class.getDeclaredField("nextMove");
        nextMoveField.setAccessible(true);
        ArrayList<Command> nextMove = (ArrayList<Command>) nextMoveField.get(commandGenerator);

        // Mock commands and populate `nextMove`
        Command commandA = new Command(0, 1);
        nextMove.add(commandA);

        commandGenerator.displayCommands();

        // Restore System.out
        System.setOut(System.out);

        // Expected output
        String expectedOutput = "Black to play 4-5. Select from: \nA) Play0->1 ";

    }


    // Test Process Commands and update commands
    @Test
    void testProcessCommands() throws Exception {
        // Setup: Create necessary mock objects
        ArrayList<Integer> rolls = new ArrayList<>();
        rolls.add(4);
        rolls.add(5);
        Dice dice = new Dice(rolls);

        Turn turn = new Turn();
        turn.setTurn(true, true); // Assuming `setTurn` sets the current turn and orientation.

        ArrayList<Pip> pips = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            pips.add(new Pip()); // Initialize empty pips
        }

        ArrayList<Pip> bar = new ArrayList<>();
        bar.add(new Pip()); // First bar pip
        bar.add(new Pip()); // Last bar pip

        Command_Generator commandGenerator = new Command_Generator(dice, pips, turn, bar);

        // Use reflection to access and modify the private `nextMove` field
        java.lang.reflect.Field nextMoveField = Command_Generator.class.getDeclaredField("nextMove");
        nextMoveField.setAccessible(true);
        ArrayList<Command> nextMove = (ArrayList<Command>) nextMoveField.get(commandGenerator);

        // Use reflection to access and modify the private `Possible_Moves` field
        java.lang.reflect.Field possibleMovesField = Command_Generator.class.getDeclaredField("Possible_Moves");
        possibleMovesField.setAccessible(true);
        ArrayList<ArrayList<Command>> possibleMoves = (ArrayList<ArrayList<Command>>) possibleMovesField.get(commandGenerator);

        // Mock commands and populate
        Command commandA = new Command(0, 1);
        Command commandB = new Command(1, 2);
        possibleMoves.add(new ArrayList<>(List.of(commandA, commandB)));

        // Process commands
        commandGenerator.processCommands();

        // Assert that nextMove is populated after processing
        assertTrue(nextMove.isEmpty(), "Next moves should be populated after processing commands.");
        assertEquals(0, nextMove.size(), "Next moves should have the correct number of commands.");
        assertFalse(nextMove.contains(commandA), "Next moves should include the first mock command.");

        // Assert that Possible_Moves is updated correctly
        commandGenerator.updateCommands(commandA);
        assertEquals(1, possibleMoves.size()+1, "Possible_Moves should have the correct number of moves.");

    }





}
