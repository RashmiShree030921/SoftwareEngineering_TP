// Hannah Nolan gitHub ID (hnolan2019) ucd
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;



class Player_IDsTest {

    //---Test for Player IDs class---------------   | is the method tested|------
    // Test returnname                              | yes
    // Test startRoll                               | yes
    // Test getPlayerNames                          | yes


    // Test getPlayerNames
    @Test
    void testGetPlayerNames() {
        // Simulate user input for player names
        String simulatedInput = "Mike\nBob\nX\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Initialise dependencies
        Turn turn = new Turn();
        ArrayList<Integer> rolls = new ArrayList<>();
        Dice dice = new Dice(rolls);

        // Create Player_IDs instance
        Player_IDs playerIDs = new Player_IDs(turn, dice, false, null);

        // Verify player names are set correctly
        assertEquals("Mike", playerIDs.returnName(false), "Player 1 should be Mike");
        assertEquals("Bob", playerIDs.returnName(true), "Player 2 should be Bob");

        // Reset System.in
        System.setIn(System.in);
    }

    // Test returnName
    @Test
    void testReturnName() {
        // Simulate user input for player names
        String simulatedInput = "Hannah\nShree\nX\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Initialise dependencies
        Turn turn = new Turn();
        ArrayList<Integer> rolls = new ArrayList<>();
        rolls.add(3); // Placeholder rolls
        rolls.add(5); // Placeholder rolls
        Dice dice = new Dice(rolls);

        // Create Player_IDs instance
        Player_IDs playerIDs = new Player_IDs(turn, dice, false, null);

        // Verify returnName returns the correct names
        assertEquals("Hannah", playerIDs.returnName(false), "Player 1 should be Hannah");
        assertEquals("Shree", playerIDs.returnName(true), "Player 2 should be Shree");

        // Reset System.in
        System.setIn(System.in);
    }

    // Test startRoll
    @Test
    void testStartRoll() {
        // Simulate user input for player names and starting roll
        String simulatedInput = "Mike\nBob\nX\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Initialise
        Turn turn = new Turn();
        ArrayList<Integer> rolls = new ArrayList<>();
        Dice dice = new Dice(rolls);

        // Create Player_IDs instance
        Player_IDs playerIDs = new Player_IDs(turn, dice, false, null);

        // Verify the starting player based on the resolved tie (FALSE  = player one)
        assertEquals(turn.returnTurn(), false, "Player 1 (Mike) should go first because they rolled higher");

        // Reset System.in
        System.setIn(System.in);
    }

    // Test startRoll with a tie
    @Test
    void testStartRoll_WithTie() {

        // Simulate user input for player names and starting roll
        String simulatedInput = "Mike\nBob\nX\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Initialise
        Turn turn = new Turn();
        ArrayList<Integer> rolls = new ArrayList<>();
        rolls.add(3); // Player 1 rolls
        rolls.add(3); // Player 2 rolls
        Dice dice = new Dice(rolls);

        // Create Player_IDs instance
        Player_IDs playerIDs = new Player_IDs(turn, dice, false, null);

        // Simulate additional rolls to resolve the tie
        rolls.clear();
        rolls.add(5); // Player 1 rolls again
        rolls.add(3); // Player 2 rolls again
        dice = new Dice(rolls);

        System.out.print(turn.returnTurn());

        // Verify the starting player based on the resolved tie (FALSE  = player one)
        assertEquals(turn.returnTurn(), false, "Player 1 (Mike) should go first because they rolled higher");

        // Reset System.in
        System.setIn(System.in);
    }



}






