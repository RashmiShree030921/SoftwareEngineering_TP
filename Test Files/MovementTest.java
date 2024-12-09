// hannah nolan git (hnolan2019@gmail.com)
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MovementTest {

    private Dice dice;
    private Turn turn;
    private ArrayList<Pip> pips;
    private ArrayList<Pip> bar;
    private Player_IDs playerIDs;
    private Movement movement;

    /// SET up and initialise the movement class
    @BeforeEach
    void setup() {
        // Initialize dependencies
        ArrayList<Integer> initialRolls = new ArrayList<>();
        initialRolls.add(3); // Simulate a roll of 3
        dice = new Dice(initialRolls);

        turn = new Turn();
        turn.setTurn(true, true); // Player 1's turn (matching orientation)

        playerIDs = new Player_IDs(turn, dice);

        // Initialize pips and bar
        pips = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            pips.add(new Pip());
        }

        bar = new ArrayList<>();
        bar.add(new Pip()); // First bar pip
        bar.add(new Pip()); // Last bar pip

        // Create Movement instance
        movement = new Movement(dice, pips, turn, bar, playerIDs);
    }

    //Implemented before ---- command select assumes can munually select move
    // early test for the movement class
    @Test
    void testMoveChecker() throws Exception {
        // Initialize dependencies
        ArrayList<Integer> rolls = new ArrayList<>();
        rolls.add(3); // Simulate a roll of 3
        Dice dice = new Dice(rolls);

        Turn turn = new Turn();
        turn.setTurn(true, true); // Player 1's turn

        ArrayList<Pip> pips = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            pips.add(new Pip());
        }

        ArrayList<Pip> bar = new ArrayList<>();
        bar.add(new Pip());
        bar.add(new Pip());

        Player_IDs playerIDs = new Player_IDs(turn, dice);

        // Create the Movement instance
        Movement movement = new Movement(dice, pips, turn, bar, playerIDs);

        // Access the `genCommands` field in Movement
        Field genCommandsField = Movement.class.getDeclaredField("genCommands");
        genCommandsField.setAccessible(true);
        Command_Generator genCommands = (Command_Generator) genCommandsField.get(movement);

        // Access the private `nextMove` field in Command_Generator
        Field nextMoveField = Command_Generator.class.getDeclaredField("nextMove");
        nextMoveField.setAccessible(true);
        ArrayList<Command> nextMove = (ArrayList<Command>) nextMoveField.get(genCommands);

        // Populate `nextMove` with a predefined Command (move from pip 0 to pip 3)
        Command testCommand = new Command(0, 3);
        nextMove.add(testCommand);

        // Add a checker for Player 1 at pip 0
        Pip pip0 = pips.get(0);
        pip0.addChecker(new Checker(true));

        // Call moveChecker
        movement.moveChecker();

        // Verify the checker has moved to pip 3
        Pip pip3 = pips.get(3);
        assertTrue(pip0.isEmpty(), "Pip 0 should be empty after the checker moves.");
        assertFalse(pip3.isEmpty(), "Pip 3 should have the checker after the move.");
    }
}
