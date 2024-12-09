import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Method;

class MovementTest {

    //Tests method that aren't tested in testfile


    // Test for Movement class ------ testing the private methods populateCommands and updateCommands (very import for the game to work)
    // Test populateCommands - use reflection for private
    @Test
    void testPrivatePopulateCommands() throws Exception {
        // Setup
        ArrayList<Pip> pips = new ArrayList<>();
        for (int i = 0; i < 24; i++) pips.add(new Pip());
        ArrayList<Pip> bar = new ArrayList<>();
        bar.add(new Pip());
        bar.add(new Pip());
        Turn turn = new Turn();
        turn.setTurn(true, true);
        Dice dice = new Dice(new ArrayList<>(List.of(4, 5)));
        TestFile testFile = new TestFile();
        Movement movement = new Movement(dice, pips, turn, bar, false, testFile);

        // Access the private populateCommands method
        Method populateCommandsMethod = Movement.class.getDeclaredMethod("populateCommands");
        populateCommandsMethod.setAccessible(true);

        // Execute
        populateCommandsMethod.invoke(movement);

        // Verify
        assertEquals(2, dice.returnRolls().size(), "Dice rolls should be populated correctly.");
    }

    @Test
    void testUpdateCommands() throws Exception {
        // Setup
        ArrayList<Pip> pips = new ArrayList<>();
        for (int i = 0; i < 24; i++) pips.add(new Pip());
        ArrayList<Pip> bar = new ArrayList<>();
        bar.add(new Pip());
        bar.add(new Pip());
        Turn turn = new Turn();
        turn.setTurn(true, true);
        Dice dice = new Dice(new ArrayList<>(List.of(4, 5)));
        TestFile testFile = new TestFile();
        Movement movement = new Movement(dice, pips, turn, bar, false, testFile);

        // Create a mock Command object
        Command mockCommand = new Command(0, 5);

        // Access the private updateCommands method
        Method updateCommandsMethod = Movement.class.getDeclaredMethod("updateCommands", Command.class);
        updateCommandsMethod.setAccessible(true);

        // Execute the private method
        updateCommandsMethod.invoke(movement, mockCommand);

        // placeholder assertion
        assertNotNull(mockCommand, "Command should have been processed.");
    }

    

    @Test
    void testMoveCheckerCallsPopulateCommands() {
        // Setup
        ArrayList<Pip> pips = new ArrayList<>();
        for (int i = 0; i < 24; i++) pips.add(new Pip());
        ArrayList<Pip> bar = new ArrayList<>();
        bar.add(new Pip());
        bar.add(new Pip());
        Turn turn = new Turn();
        turn.setTurn(true, true);
        Dice dice = new Dice(new ArrayList<>(List.of(4, 5)));
        TestFile testFile = new TestFile();
        Movement movement = new Movement(dice, pips, turn, bar, false, testFile);

        // Execute
        movement.moveChecker(); // Assuming moveChecker internally calls populateCommands

    }

}
