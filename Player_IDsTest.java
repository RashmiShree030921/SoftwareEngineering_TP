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

    //test name
    @Test
    void testName() {

        // Simulate user input for player names and 'X' to continue
        String simulatedInput = "Alice\nBob\nX\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Initialise
        Turn turn = new Turn();
        ArrayList<Integer> rolls = new ArrayList<>();
        rolls.add(4); // Simulate Player 1 rolling 4
        rolls.add(6); // Simulate Player 2 rolling 6
        Dice dice = new Dice(rolls);

        // Create Player_IDs instance
        Player_IDs playerIDs = new Player_IDs(turn, dice);

        // Verify that the returnName method returns the correct name
        assertEquals(playerIDs.returnName(true), "Bob", "Player 2 should be Bob");
        assertEquals(playerIDs.returnName(false), "Alice", "Player 1 should be Alice");
    }

    //|Test getPlayerNames
    @Test
    void testGetPlayerNames() {
        // Simulate user input for player names and 'X' to continue
        String simulatedInput = "Alice\nBob\nX\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Initialise
        Turn turn = new Turn();
        ArrayList<Integer> rolls = new ArrayList<>();
        rolls.add(4); // Simulate Player 1 rolling 4
        rolls.add(6); // Simulate Player 2 rolling 6
        Dice dice = new Dice(rolls);

        // Create Player_IDs instance
        Player_IDs playerIDs = new Player_IDs(turn, dice);

        // Verify that the getPlayerNames method sets the correct names
        assertEquals(playerIDs.returnName(true), "Bob", "Player 2 should be Bob");
        assertEquals(playerIDs.returnName(false), "Alice", "Player 1 should be Alice");

        // Reset System.in after test
        System.setIn(System.in);
    }

    // tests setting names and returning rolls
    @Test
    void testStartRoll() {
        // Simulate user input for player names and 'X' to continue
        String simulatedInput = "Alice\nBob\nX\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Initialise dependencies
        Turn turn = new Turn();
        ArrayList<Integer> rolls = new ArrayList<>();
        rolls.add(4); // Simulate Player 1 rolling 4
        rolls.add(6); // Simulate Player 2 rolling 6
        Dice dice = new Dice(rolls);

        // Create Player_IDs instance
        Player_IDs playerIDs = new Player_IDs(turn, dice);

        // Verify the starting player based on dice rolls
        assertTrue(turn.returnTurn(), "Player 2 (Bob) should go first because they rolled higher");

        // Reset System.in after test
        System.setIn(System.in);
    }

}






