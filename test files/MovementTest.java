import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.function.Supplier;

class MovementTest {

    // Test in movement test all process commmand (hint, pip, dice, new match, valid moves, invalid moves) Test via display

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


    // Test for moveChecker method (Test it moves a checker from one pip to another) - only tests 1 move for unit test
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
        pips.get(0).addChecker(new Checker(true)); // Add a black checker to pip 0
        pips.get(6).addChecker(new Checker(false)); // Add a blocking checker to pip 6
        pips.get(10).addChecker(new Checker(true)); // Add a balck checker to pip 10
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
        Method processCommands = Movement.class.getDeclaredMethod("processCommand"); // need to change from the scan user input - to the simulated input
        processCommands.setAccessible(true);

        // calling the method we are testing  - process commands is being called part of this
        movement.moveChecker();

        // Test the display works after the move
        movement.Display();

        // Tet is confirmed by the display
        simulated_IN = "#\nA\n";
        System.setIn(new ByteArrayInputStream(simulated_IN.getBytes()));

    }

    // Test for process commands  - not test mode
    // try and enter hint, pip, invalid moves,
    @Test
    void process_commands_test() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {


        // Set player IDs
        // Simulate user input for player names and starting roll
        String simulatedInput = "Mike\nBob\nX\nA\nhint\npip";
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
        pips.get(0).addChecker(new Checker(true)); // Add ablack checker to pip 0
        pips.get(6).addChecker(new Checker(false)); // Add a blocking checker to pip 6
        pips.get(10).addChecker(new Checker(true)); // Add a black checker to pip 10
        dice.rollDice(true);
        generator.processCommands();

        // Test (1): test for hint, pip, and invalid command
        //simulate user input
        String simulated_IN = "hint\npip\n#\nA\n";
        System.setIn(new ByteArrayInputStream(simulated_IN.getBytes()));

        // Test the displayCommands method
        generator.displayCommands("PlayerName"); // Verify it runs without errors

        // Test (1) Enure that the checkers are moved for valid inputs
        Movement movement = new Movement(dice, pips, turn, bar, false, testFile, playerIDs);


        // Replace the Scanner in Movement with one reading from the simulated input
        Field scanField = Movement.class.getDeclaredField("scan"); // Need to bypass the scann in the movement class
        scanField.setAccessible(true);  //make access possible
        scanField.set(movement, new Scanner(System.in));

        // change process commands manually to test move checker - use reflection to access private data
        Method processCommands = Movement.class.getDeclaredMethod("processCommand"); // need to change from the scan user input - to the simulated input
        processCommands.setAccessible(true);

        movement.moveChecker();

        // Test the display works after the move - validate test from display
        movement.Display();
    }

    //-------------------------Process COmmands Test--------------------------------------------
    // Test for process commands  - to ensure that a new match can be started
    // Test for process commands  - not test mode
    // try and enter hint, pip, invalid moves,
    @Test
    void process_commands_test_new_match() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        // Set player IDs
        // Simulate user input for player names and starting roll
        String simulatedInput = "Mike\nBob\nX\n";
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
        pips.get(0).addChecker(new Checker(true)); // Add a black checker to pip 0
        pips.get(6).addChecker(new Checker(false)); // Add a blocking checker to pip 6
        pips.get(10).addChecker(new Checker(true)); // Add a balck to pip 10
        dice.rollDice(true);
        generator.processCommands();

        // Test (1): test for hint - new match
        //simulate user input
        String simulated_IN = "hint\nnew match\nyes\n";
        System.setIn(new ByteArrayInputStream(simulated_IN.getBytes()));

        // Test the displayCommands method
        generator.displayCommands("PlayerName"); // Verify it runs without errors

        // Test (1) Enusure new match command is processed
        Movement movement = new Movement(dice, pips, turn, bar, false, testFile, playerIDs);


        // Replace the Scanner in Movement with one reading from the simulated input
        Field scanField = Movement.class.getDeclaredField("scan"); // Need to bypass the scann in the movement class
        scanField.setAccessible(true);  //make access possible
        scanField.set(movement, new Scanner(System.in));

        // change process commands manually to test move checker - use reflection to access private data
        Method processCommands = Movement.class.getDeclaredMethod("processCommand"); // need to change from the scan user input - to the simulated input
        processCommands.setAccessible(true);

        movement.moveChecker();

        // Test the display works after the move - validate test from display
        movement.Display();
    }

    // Test Process commands and ensure that the dice command can be used properly
    @Test
    void process_commands_test_dice_doubles() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        // Set player IDs
        // Simulate user input for player names and starting roll
        String simulatedInput = "Mike\nBob\nX\n";
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
        pips.get(0).addChecker(new Checker(true)); // Add a black checker to pip 0
        pips.get(6).addChecker(new Checker(false)); // Add a blocking checker to pip 6
        pips.get(10).addChecker(new Checker(true)); // Add a black checker to pip 10
        dice.rollDice(true);
        generator.processCommands();

        // Test (1): test for dice command
        //simulate user input - tests doubles "yes" and dice
        String simulated_IN = "doubles\nyes\ndice\ndice66\nA\n";
        System.setIn(new ByteArrayInputStream(simulated_IN.getBytes()));

        // Test the displayCommands method
        generator.displayCommands("PlayerName"); // Verify it runs without errors

        // Test (1) Enusure new match command is processed
        Movement movement = new Movement(dice, pips, turn, bar, false, testFile, playerIDs);


        // Replace the Scanner in Movement with one reading from the simulated input
        Field scanField = Movement.class.getDeclaredField("scan"); // Need to bypass the scann in the movement class
        scanField.setAccessible(true);  //make access possible
        scanField.set(movement, new Scanner(System.in));

        // change process commands manually to test move checker - use reflection to access private data
        Method processCommands = Movement.class.getDeclaredMethod("processCommand"); // need to change from the scan user input - to the simulated input
        processCommands.setAccessible(true);

        movement.moveChecker();

        // Test the display works after the move - validate test from display
        movement.Display();

    }


    // Test
    @Test
    void testStartNewMatch_and_restaart() {
        // Set up
        String simulatedInput = "Mike\nBob\nX\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Initialise
        ArrayList<Integer> rolls = new ArrayList<>();
        dice = new Dice(rolls);
        Player_IDs playerIDs = new Player_IDs(turn, dice, false, null);
        System.setIn(System.in);
        Command_Generator generator = new Command_Generator(dice, pips, turn, bar, testFile, false);

        // Populate pips
        pips.get(0).addChecker(new Checker(true)); // Add a black checker to pip 0
        pips.get(6).addChecker(new Checker(false)); // Add a blocking checker to pip 6
        pips.get(10).addChecker(new Checker(true)); // Add a black checker to pip 10
        dice.rollDice(true);
        generator.processCommands();

        // Test (1): test for dice command
        //simulate user input - request new match and then see if the restart flag is set
        String simulated_IN = "new match\nyes\n";
        System.setIn(new ByteArrayInputStream(simulated_IN.getBytes()));

        // Test the displayCommands method
        generator.displayCommands("PlayerName"); // Verify it runs without errors

        // Test (1) Enusure new match command is processed
        Movement movement = new Movement(dice, pips, turn, bar, false, testFile, playerIDs);

        movement.new_match();

        // Assert that restartFlag is set to true
        assertTrue(movement.restartFlag, "Restart flag should be true for 'yes' response");
    }

    // Test ensure that when the user selects no for start a new match that the restart flag is still to false
    @Test
    void testStartNewMatch_and_restart() {
        // Set up
        String simulatedInput = "Mike\nBob\nX\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Initialise
        ArrayList<Integer> rolls = new ArrayList<>();
        dice = new Dice(rolls);
        Player_IDs playerIDs = new Player_IDs(turn, dice, false, null);
        System.setIn(System.in);
        Command_Generator generator = new Command_Generator(dice, pips, turn, bar, testFile, false);

        // Populate pips
        pips.get(0).addChecker(new Checker(true)); // Add a black checker to pip 0
        pips.get(6).addChecker(new Checker(false)); // Add a blocking checker to pip 6
        pips.get(10).addChecker(new Checker(true)); // Add a black checker to pip 10
        dice.rollDice(true);
        generator.processCommands();

        // Test (1): test for dice command
        //simulate user input - request new match and then see if the restart flag is set
        String simulated_IN = "new match\nno\n";
        System.setIn(new ByteArrayInputStream(simulated_IN.getBytes()));

        // Test the displayCommands method
        generator.displayCommands("PlayerName"); // Verify it runs without errors

        // Test (1) Enusure new match command is processed
        Movement movement = new Movement(dice, pips, turn, bar, false, testFile, playerIDs);

        movement.new_match();

        // Assert that restartFlag is set to true
        assertFalse(movement.restartFlag, "Restart flag should be true for 'yes' response");
    }

    // Test for calculate score method
    // Ensures that the score is not null after move is made
    @Test
    void testCalculateScore() {
        // Set up
        String simulatedInput = "Mike\nBob\nX\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Initialise
        ArrayList<Integer> rolls = new ArrayList<>();
        dice = new Dice(rolls);
        Player_IDs playerIDs = new Player_IDs(turn, dice, false, null);
        System.setIn(System.in);
        Command_Generator generator = new Command_Generator(dice, pips, turn, bar, testFile, false);

        // Populate pips
        pips.get(0).addChecker(new Checker(true)); // Add a black checker to pip 0
        pips.get(6).addChecker(new Checker(false)); // Add a blocking checker to pip 6
        pips.get(10).addChecker(new Checker(true)); // Add a black checker to pip 10
        dice.rollDice(true);
        generator.processCommands();

        // Test (1): test for dice command
        //simulate user input - request new match and then see if the restart flag is set
        String simulated_IN = "A\nA\nA";
        System.setIn(new ByteArrayInputStream(simulated_IN.getBytes()));

        // Test the displayCommands method
        generator.displayCommands("PlayerName"); // Verify it runs without errors

        // Test (1) Enusure new match command is processed
        Movement movement = new Movement(dice, pips, turn, bar, false, testFile, playerIDs);

        movement.moveChecker();


        // Calculate the score
        movement.calculateScore();
        movement.Display();

        int score = movement.calculateScore();
        System.out.print("Score: " + score);

        // Assert that the score not null after move is made
        assertNotNull(movement.calculateScore(), "Score should be calculated");
    }

}
