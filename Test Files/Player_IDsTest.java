// Hannah Nolan gitHub ID (hnolan2019) ucd
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.lang.reflect.Field;


class Player_IDsTest {

    //---Test for Player IDs class---------------   | is the method tested|------
    // Test returnname                              | yes
    // Test startRoll                               | yes
    // Test getPlayerNames                          | no


    // Test getPlayerNames
    @Test
    void testGetPlayerNames() {
        // Simulate user input for player names
        String simulatedInput = "Alice\nBob\nX\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Initialise dependencies
        Turn turn = new Turn();
        ArrayList<Integer> rolls = new ArrayList<>();
        rolls.add(2); // Placeholder rolls
        rolls.add(3); // Placeholder rolls
        Dice dice = new Dice(rolls);

        // Create Player_IDs instance
        Player_IDs playerIDs = new Player_IDs(turn, dice, false, null);

        // Verify player names are set correctly
        assertEquals("Alice", playerIDs.returnName(false), "Player 1 should be Alice");
        assertEquals("Bob", playerIDs.returnName(true), "Player 2 should be Bob");

        // Reset System.in
        System.setIn(System.in);
    }

    // Test returnName
    @Test
    void testReturnName() {
        // Simulate user input for player names
        String simulatedInput = "Alice\nBob\nX\n";
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
        assertEquals("Alice", playerIDs.returnName(false), "Player 1 should be Alice");
        assertEquals("Bob", playerIDs.returnName(true), "Player 2 should be Bob");

        // Reset System.in
        System.setIn(System.in);
    }

    // Test startRoll
    @Test
    void testStartRoll() {
        // Simulate user input for player names and starting roll
        String simulatedInput = "Alice\nBob\nX\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Initialise
        Turn turn = new Turn();
        ArrayList<Integer> rolls = new ArrayList<>();
        rolls.add(6); // Simulate Player 1 rolling 6
        rolls.add(4); // Simulate Player 2 rolling 4
        Dice dice = new Dice(rolls);

        // Create Player_IDs instance
        Player_IDs playerIDs = new Player_IDs(turn, dice, false, null);

        // Verify the starting player based on dice rolls
        assertFalse(turn.returnTurn(), "Player 1 (Alice) should go first because they rolled higher");

        // Reset System.in
        System.setIn(System.in);
    }

    // Test startRoll with a tie
    @Test
    void testStartRollWithTie() {
        // Simulate user input for player names and starting roll
        String simulatedInput = "Alice\nBob\nX\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Initialize dependencies
        Turn turn = new Turn();
        ArrayList<Integer> rolls = new ArrayList<>();
        rolls.add(4); // Simulate Player 1 rolling 4
        rolls.add(4); // Simulate Player 2 rolling 4
        Dice dice = new Dice(rolls);

        // Create Player_IDs instance
        Player_IDs playerIDs = new Player_IDs(turn, dice, false, null);

        // Simulate additional rolls to resolve the tie
        rolls.clear();
        rolls.add(5); // Player 1 rolls again
        rolls.add(3); // Player 2 rolls again

        // Verify the starting player based on the resolved tie
        assertFalse(turn.returnTurn(), "Player 1 (Alice) should go first because they rolled higher after the tie");

        // Reset System.in
        System.setIn(System.in);
    }



}






