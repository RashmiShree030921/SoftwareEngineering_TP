// Hannah Nolan gitHub ID (hnolan2019) ucd
// The select command is tested fully in the testFile as it reads in multiple selections

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.lang.reflect.Field;
import java.util.Scanner;

// to implement Reflection
// www.geeksforgeeks.org/how-to-access-private-field-and-method-using-reflection-in-java/
// was used as a reference

// The private implemented as part of the public methods
//by testing the public methods we are also testing the private methods

class Command_GeneratorTest {

    //Public Methods in Command_Generator ------ ---| current tests |
// Select Command                                   | finished      |
    // displayCommands                              | finished      |
    // processCommands                              | finished      |
    // returnTotalSteps                             | finished      |
    // clearCommands                                | finished      |

    //Select COmmand - test includes rolling dice and then choosing one of the given optiosn from list

    // Test for the Select command method and display commands method
    // test only included non -est mode (user input)
    @Test
    void testRollDiceAndSelectCommand() throws Exception {
        // Setup + declare
        ArrayList<Pip> pips = new ArrayList<>();
        for (int i = 0; i < 24; i++) pips.add(new Pip());
        ArrayList<Pip> bar = new ArrayList<>();
        bar.add(new Pip());
        bar.add(new Pip());
        Turn turn = new Turn();
        turn.setTurn(true, true);
        Dice dice = new Dice(new ArrayList<>(List.of(4, 5)));
        TestFile testFile = new TestFile();

        // Call Command_Generator
        Command_Generator generator = new Command_Generator(dice, pips, turn, bar, testFile, false);

        // Populate pips
        pips.get(0).addChecker(new Checker(true)); // Add a white checker to pip 0
        dice.rollDice(true);
        generator.processCommands();

        // Verify the state of nextMove
        Field nextMoveField = Command_Generator.class.getDeclaredField("nextMove");
        nextMoveField.setAccessible(true); // Bypass private access
        @SuppressWarnings("unchecked")
        List<Command> nextMove = (List<Command>) nextMoveField.get(generator);

        // Ensure NextMove is not empty after rolling dice
        assertFalse(nextMove.isEmpty(), "nextMove should not be empty after rolling dice.");

        // Test the displayCommands method
        generator.displayCommands("PlayerName"); // Pass the required argument

        // Test (1) the selectCommand method
        String commandInput = "A"; // Simulate user selecting the first command
        Command selectedCommand = generator.selectCommand(commandInput);
        assertNotNull(selectedCommand, "Selected command should not be null.");
        assertTrue(nextMove.contains(selectedCommand), "Selected command should be in nextMove.");

        // Test (2) invalid command
        String invalidCommand = "-";
        Command invalidSelection = generator.selectCommand(invalidCommand);
        assertNull(invalidSelection, "Invalid command should return null.");

        // Test (3) Clear is NextMove is empty before selecting a command, should return null
        nextMove.clear(); // clear any existing moves

        // Test invalid command with empty nextMove
        commandInput = "A";
        selectedCommand = generator.selectCommand(commandInput);
        assertNull(selectedCommand, "Command selection should return null when nextMove is empty.");
    }

    // Test for Return Total Steps in command method:
    @Test
    void testReturnTotalSteps() {
        // Setup + declare
        ArrayList<Pip> pips = new ArrayList<>();
        for (int i = 0; i < 24; i++) pips.add(new Pip());
        ArrayList<Pip> bar = new ArrayList<>();
        bar.add(new Pip());
        bar.add(new Pip());
        Turn turn = new Turn();
        turn.setTurn(true, true);
        Dice dice = new Dice(new ArrayList<>(List.of(4, 5)));
        TestFile testFile = new TestFile();

        // Call Command_Generator
        Command_Generator generator = new Command_Generator(dice, pips, turn, bar, testFile, false);

        // Populate pips
        pips.get(0).addChecker(new Checker(true)); // Add a white checker to pip 0
        dice.rollDice(true);
        generator.processCommands();

        // Test (1)
        // Verify returnTotalSteps is greater than 0 after processing commands
        int totalSteps = generator.returnTotalSteps();
        assertTrue(totalSteps > 0, "Total steps should be greater than 0 after processing commands.");
        System.out.println("Total Steps: " + totalSteps);

        // Test (2)
        // verify after clearing the commands the total steps is 0
        // Call clearCommands
        generator.clearCommands();
        totalSteps = generator.returnTotalSteps();
        assertEquals(0, totalSteps, "Total steps should be reset to 0 after clearCommands.");
        System.out.println("Total Steps after clearCommands: " + totalSteps);
    }

    // Test for clear method (Test it clears commands and once cleared no commands are available)
    @Test
    void testClearCommands() {
        // Setup + declare
        ArrayList<Pip> pips = new ArrayList<>();
        for (int i = 0; i < 24; i++) pips.add(new Pip());
        ArrayList<Pip> bar = new ArrayList<>();
        bar.add(new Pip());
        bar.add(new Pip());
        Turn turn = new Turn();
        turn.setTurn(true, true);
        Dice dice = new Dice(new ArrayList<>(List.of(4, 5)));
        TestFile testFile = new TestFile();

        // Call Command_Generator
        Command_Generator generator = new Command_Generator(dice, pips, turn, bar, testFile, false);

        // Populate pips
        pips.get(0).addChecker(new Checker(true)); // Add a white checker to pip 0
        dice.rollDice(true);
        generator.processCommands();

        // Verify returnTotalSteps is greater than 0 after processing commands
        int totalSteps = generator.returnTotalSteps();
        assertTrue(generator.returnTotalSteps() > 0, "Total steps should be greater than 0 before clearCommands.");

        // Call clearCommands
        generator.clearCommands();

        // Test (1): Ensure that the total stepsare reset to 0 after clearCommands
        int totalStepsAfterClear = generator.returnTotalSteps();
        System.out.println("Total Steps after clearCommands: " + totalStepsAfterClear);
        assertEquals(0, totalStepsAfterClear, "Total steps should be reset to 0 after clearCommands.");


        // Test (2): Verify that no commands are available for selection
        Command result = generator.selectCommand("A");
        assertNull(result, "No commands should be available after clearCommands.");
    }



}






