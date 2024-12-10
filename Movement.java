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


    // set upu the conditions for the test to run
    @BeforeEach
    void setup() {
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


    // Tests to see if the moveChecker can accurately move a checker from one pip to another
    @Test
    void moveChecker() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        pips.get(2).addChecker(new Checker(true)); // Add a black checker to pip 0
        pips.get(0).addChecker(new Checker(false)); // Add a blocking checker to pip 23

        String simulatedInput = "Mike\nBob\nX\nA\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Initialise
        ArrayList<Integer> rolls = new ArrayList<>();
        Dice dice = new Dice(rolls);

        Command_Generator generator = new Command_Generator(dice, pips, turn, bar, testFile, false);

        // Create Player_IDs instance
        Player_IDs playerIDs = new Player_IDs(turn, dice, false, null);

        // Test (1) Enusure new match command is processed
        Movement movement = new Movement(dice, pips, turn, bar, false, testFile, playerIDs);

        //simulate user input
        for (int i = 0; i < 12; i++) {
            String simulated_IN = "A\nA\nA\nA\nA\nA\nA\nA";
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
            movement.Display();

            // Manually access the removedCheckers - to check the win condition - reflection for private
            Field removedCheckers = Movement.class.getDeclaredField("removedCheckers"); // Need to bypass
            removedCheckers.setAccessible(true);  //make access possible
            removedCheckers.set(movement, new int[]{14, 0}); // replace with 15 checkers

            movement.moveChecker();
            movement.Display();

        }
    }

    // Test for checkWin method (Test it returns false when no player has won, and true when a player has won)
    @Test
    void testCheckWin() throws NoSuchFieldException, IllegalAccessException {

        // test (1): Invalid win
        // No Player Ids set up - so no player can win
        Movement movement = new Movement(dice, pips, turn, bar, false, testFile, null);

        // Should return false as no player has won
        assertFalse(movement.checkWin(), "No player should have won yet");

        // Test (2): Player 1 wins - Valid entry
        // Manually access the removedCheckers - to check the win condition - reflection for private
        Field removedCheckers = Movement.class.getDeclaredField("removedCheckers"); // Need to bypass
        removedCheckers.setAccessible(true);  //make access possible
        removedCheckers.set(movement, new int[]{15, 0}); // replace with 15 checkers

        // Checks if player 2 has one (black checkers)
        assertTrue(movement.checkWin(), "A player should have won");

        // Test (3): Player 2 wins - invalid entry
        removedCheckers.set(movement, new int[]{3, 0});

        // Checks if player 2 has one (black checkers)
        assertFalse(movement.checkWin(), "A player should not have won");
    }

    // Test to see if the find win method can accurately find a win when the checkers are removed
    // Player to first remove all pieces from the board wins
    @Test
    void findWin() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        pips.get(2).addChecker(new Checker(true)); // Add a black checker to pip 0
        pips.get(0).addChecker(new Checker(false)); // Add a blocking checker to pip 23

        String simulatedInput = "Mike\nBob\nX\nA\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Initialise
        ArrayList<Integer> rolls = new ArrayList<>();
        Dice dice = new Dice(rolls);

        Command_Generator generator = new Command_Generator(dice, pips, turn, bar, testFile, false);

        // Create Player_IDs instance
        Player_IDs playerIDs = new Player_IDs(turn, dice, false, null);

        // Test (1) Enusure new match command is processed
        Movement movement = new Movement(dice, pips, turn, bar, false, testFile, playerIDs);

        //simulate user input
        for (int i = 0; i < 3; i++) {
            String simulated_IN = "A\nA";
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
            movement.Display();

            // Manually access the removedCheckers - to check the win condition - reflection for private
            Field removedCheckers = Movement.class.getDeclaredField("removedCheckers"); // Need to bypass
            removedCheckers.setAccessible(true);  //make access possible
            removedCheckers.set(movement, new int[]{14, 0}); // replace with 15 checkers
            removedCheckers.setAccessible(false); // close accessbility


            movement.moveChecker();
            movement.Display();

        }

        boolean win = movement.findWin();
        System.out.println("Win: " + win);

        boolean test = movement.checkWin();
        System.out.println("Test: " + test);
    }


    @Test
    void calculateScore() {
    }

    // Test the condition for winning
    @Test
    void checkWin() {


    }


    // Test for process commands  - not test mode
    // try and enter hint, pip, invalid moves,
    @Test
    void process_commands_test() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        // Set player IDs
        // Simulate user input for player names and starting roll
        String simulatedInput = "Mike\nBob\nX\nhint\npip\nA\n";
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

        // if pip is selected then the prcoess commands method will call the pipCount method which returns the pip count by printing the count
        // Call the pipCOunt to manually compare the output of movement to the direct call of pipCount
        movement.pipCount();

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


    // Test restart flag
    // set restart flag
    // reset board
    @Test
    void returnRestart() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        pips.get(2).addChecker(new Checker(true)); // Add a black checker to pip 0
        pips.get(0).addChecker(new Checker(false)); // Add a blocking checker to pip 23

        String simulatedInput = "Mike\nBob\nX\nA\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Initialise
        ArrayList<Integer> rolls = new ArrayList<>();
        Dice dice = new Dice(rolls);

        Command_Generator generator = new Command_Generator(dice, pips, turn, bar, testFile, false);

        // Create Player_IDs instance
        Player_IDs playerIDs = new Player_IDs(turn, dice, false, null);

        // Test (1) Enusure new match command is processed
        Movement movement = new Movement(dice, pips, turn, bar, false, testFile, playerIDs);

        //simulate user input
        for (int i = 0; i < 2; i++) {
            String simulated_IN = "A\nA\nA\nA\nA\nA\nA\nA";
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
            movement.Display();

            // Manually access the removedCheckers - to check the win condition - reflection for private
            Field removedCheckers = Movement.class.getDeclaredField("removedCheckers"); // Need to bypass
            removedCheckers.setAccessible(true);  //make access possible
            removedCheckers.set(movement, new int[]{14, 0}); // replace with 15 checkers

            movement.moveChecker();
            movement.Display();
        }

        // restart flag should be false
        assertEquals(false, movement.returnRestart(), "Restart should be false");

        movement.setRestart(true);

        // change the restart flag to true
        movement.returnRestart();

        // Ensure that there is no movement after calling movececker
        movement.moveChecker();

        //no chaneg should be seen in display
        movement.Display();
        System.out.println("Restart: " + movement.returnRestart());

        // Ensure that the restart flag is equal to true after being set
        assertEquals(true, movement.returnRestart(), "Restart should be true");

        // Ensure that the reset flag is false after restart
        movement.resetBoard();
        assertEquals(false, movement.returnRestart(), "Restart should be false after reset");


    }

}
