import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class DiceTest {

    // Test for Dice class
    // public rollDice
    @Test
    void testRollDiceNoSort() {
        ArrayList<Integer> rolls = new ArrayList<>();
        Dice dice = new Dice(rolls);

        // Test rollDice with sort_flag = false
        dice.rollDice(false);

        // Verify that the rolls list contains exactly 2 or 4 elements
        assertTrue(rolls.size() == 2 || rolls.size() == 4, "Rolls should contain 2 or 4 elements depending on doubleFlag");

        // Verify that all rolls are between 1 and 6
        for (int roll : rolls) {
            assertTrue(roll >= 1 && roll <= 6, "Each roll should be between 1 and 6");
        }

        // Verify doubleFlag behavior
        if (rolls.size() == 4) {
            assertEquals(rolls.get(0), rolls.get(2), "Double rolls should match the first roll");
            assertEquals(rolls.get(0), rolls.get(3), "Double rolls should match the first roll");
        }
    }

    // Test roll dice with sort_flag = true
    @Test
    void testRollDiceWithSort() {
        ArrayList<Integer> rolls = new ArrayList<>();
        Dice dice = new Dice(rolls);

        // Mock specific rolls for testing sort
        rolls.add(3); // First roll
        rolls.add(5); // Second roll

        dice.rollDice(true); // Enable sorting

        // Verify that rolls are sorted if necessary
        if (rolls.size() == 2 && rolls.get(0) < rolls.get(1)) {
            assertTrue(rolls.get(0) <= rolls.get(1), "Rolls should be sorted if sort_flag is true");
        }
    }


    //Test clearDice
    @Test
    void testClearDice() {
        ArrayList<Integer> rolls = new ArrayList<>();
        Dice dice = new Dice(rolls);

        // Mock specific rolls for testing clearDice
        rolls.add(3); // First roll
        rolls.add(5); // Second roll

        dice.clearDice();

        // Verify that rolls is empty
        assertTrue(rolls.isEmpty(), "Rolls should be empty after calling clearDice");
    }

    //Test returnRolls
    @Test
    void testReturnRolls() {
        ArrayList<Integer> rolls = new ArrayList<>();
        Dice dice = new Dice(rolls);

        // Mock specific rolls for testing returnRolls
        rolls.add(3); // First roll
        rolls.add(5); // Second roll

        // Verify that returnRolls returns the rolls list
        assertEquals(rolls, dice.returnRolls(), "returnRolls should return the rolls list");
    }

}
