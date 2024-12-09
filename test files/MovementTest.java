import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Method;
import java.util.Scanner;

class MovementTest {

    //declare
    private Movement movement;
    private ArrayList<Pip> pips;
    private ArrayList<Pip> bar;
    private Dice dice;
    private Turn turn;
    private TestFile testFile;
    private Player_IDs IDs;

    // MoveChecker is fully tested in testmode of the testFile


    // set upu the conditions for the test to run
    @BeforeEach
    void setup(){
        pips = new ArrayList<>();
        for (int i = 0; i < 24; i++) pips.add(new Pip());
        bar = new ArrayList<>();
        bar.add(new Pip());
        bar.add(new Pip());
        turn = new Turn();
        turn.setTurn(true, true);
        dice = new Dice(new ArrayList<>(List.of(4, 5)));
        testFile = new TestFile();

    }

    // Test for checkWin method (Test it returns false when no player has won)
    @Test
    void testCheckWin() {

        Movement movement = new Movement(dice, pips, turn, bar, false, testFile, null);

        assertFalse(movement.checkWin(), "No player should have won yet");
    }



    // Test for moveChecker method (Test it moves a checker from one pip to another) - only tests 1 move
    @Test
    void wtestSelectCommandAndMoveChecker() throws Exception {

        // Set player IDs
        // Simulate user input for player names and starting roll
        String simulatedInput = "Mike\nBob\nX\nA\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Initialise
        ArrayList<Integer> rolls = new ArrayList<>();
        dice = new Dice(rolls);

        // Create Player_IDs instance
        Player_IDs playerIDs = new Player_IDs(turn, dice, false, null);

        // Reset System.in
        System.setIn(System.in);

        //call the command generator
        Command_Generator generator = new Command_Generator(dice, pips, turn, bar, testFile, false);

        // Populate pips
        pips.get(0).addChecker(new Checker(true)); // Add a white checker to pip 0
        pips.get(6).addChecker(new Checker(false)); // Add a blocking checker to pip 6
        pips.get(10).addChecker(new Checker(true)); // Add a white checker to pip 10
        dice.rollDice(true);
        generator.processCommands();


        // Test the displayCommands method
        generator.displayCommands("PlayerName"); // Verify it runs without errors

        // Test (1) Enure that the checkers are moved for valid inputs
        Movement movement = new Movement(dice, pips, turn, bar, false, testFile, playerIDs);

        //simulate user input
        String simulated_IN = "A\nA\n";
        System.setIn(new ByteArrayInputStream(simulated_IN.getBytes()));

        // Replace the Scanner in Movement with one reading from the simulated input
        Field scanField = Movement.class.getDeclaredField("scan"); // Need to bypass the scann in the movement class
        scanField.setAccessible(true);  //make access possible
        scanField.set(movement, new Scanner(System.in));

        // change process commands manually to test move checker - use reflection to access private data
        Method processCommands = Movement.class.getDeclaredMethod("processCommand"); // need to change from the scan user input
        processCommands.setAccessible(true);

        // calling the method we are testing  - process commands is being called part of this
        movement.moveChecker();

        movement.Display();

        //Test (2) Ensure that the checkers are not moved for invalid inputs and error message is correctly displayed
        simulated_IN = "#\nA\n";
        System.setIn(new ByteArrayInputStream(simulated_IN.getBytes()));

        // calling the method we are testing  - process commands is being called part of this
        movement.moveChecker();

        movement.Display();
    }

}
