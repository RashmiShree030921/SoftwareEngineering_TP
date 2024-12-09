// Hannah Nolan gitHub ID (hnolan2019) ucd
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class Move_CheckTest {

    //---Test for Move_Check class---------------   | is the method tested|------
    // Test updateBarFlag                               | yes
    // returnBarFlag                                    | yes
    // Test setBarChecker                               | yes
    // Test returnBarChecker                            | yes
    // convert2AlterIndex                               | yes
    // homeCheck                                        | yes
    // canMove()                                        | yes
    // canMoveOff                                       | yes
    // barCount()                                       | yes


    // Declare class variables
    private final int num_pips = 24;
    private ArrayList<Pip> Pips;
    private ArrayList<Pip> Bar;

    private Turn turn;
    private boolean barFlag = false;


    // Test updateBarFlag and ReturnBarFlag to test what happens when bar is emppty vs when bar has checkers
    // Purpose: Ensures the barFlag behaves correctly depending on the state of the Bar
    @org.junit.jupiter.api.Test
    void testUpdateBarFlag_and_ReturnBarFlag() {

        // Create mock data for Pips and Bar
        ArrayList<Pip> Pips = new ArrayList<>();
        ArrayList<Pip> Bar = new ArrayList<>();
        Bar.add(new Pip()); // Bar[0] for orientation == turn
        Bar.add(new Pip()); // Bar[1] for orientation != turn

        Turn turn = new Turn();
        turn.setTurn(true, true);  // Turn with matching orientation
        Move_Check moveCheck = new Move_Check(Pips, turn, Bar);

        // Test when Bar is empty - check what happens to update bar flag when bar is empty
        moveCheck.updateBarFlag();
        assertFalse(moveCheck.returnBarFlag(), "Bar flag should be false when Bar is empty.");

        // Test when Bar has checkers
        Pip pip = new Pip();
        pip.addChecker(new Checker(true)); // Add a checker to the pip
        Bar.set(0, pip); // Mock Bar[0] with a checker
        moveCheck.updateBarFlag();
        assertTrue(moveCheck.returnBarFlag(), "Bar flag should be true when Bar has checkers.");

        moveCheck.setBarFlag(true);
        assertTrue(moveCheck.returnBarFlag(), "Should return true when barFlag is manually set to true.");

        moveCheck.setBarFlag(false);
        assertFalse(moveCheck.returnBarFlag(), "Should return false when barFlag is manually set to false.");
    }

    // Test for setBarFlag and returnBarFlag
    @org.junit.jupiter.api.Test
    void testSetBarChecker_and_ReturnBarChecker() {
        // Create mock data for Pips and Bar
        ArrayList<Pip> Pips = new ArrayList<>();
        ArrayList<Pip> Bar = new ArrayList<>();
        Bar.add(new Pip()); // Bar[0] for orientation == turn
        Bar.add(new Pip()); // Bar[1] for orientation != turn

        Turn turn = new Turn();
        turn.setTurn(true, true);  // Turn with matching orientation
        Move_Check moveCheck = new Move_Check(Pips, turn, Bar);

        // Test for setting and returning Bar Checker
        Pip pip = new Pip();
        pip.addChecker(new Checker(true)); // Add a checker to the pip
        moveCheck.setBarFlag(true);
        assertEquals(true, moveCheck.returnBarFlag(), "Bar checker should be set to the pip.");

        moveCheck.setBarFlag(false);
        assertEquals(false, moveCheck.returnBarFlag(), "Bar checker should be set to the pip.");
    }

    // Test the convert2AlterIndex method
    @org.junit.jupiter.api.Test
    void testConvert2AlterIndex() {
        // Create mock data for Pips and Bar
        ArrayList<Pip> Pips = new ArrayList<>();
        ArrayList<Pip> Bar = new ArrayList<>();
        Bar.add(new Pip()); // Bar[0] for orientation == turn
        Bar.add(new Pip()); // Bar[1] for orientation != turn

        Turn turn = new Turn();
        turn.setTurn(true, true);  // Turn with matching orientation
        Move_Check moveCheck = new Move_Check(Pips, turn, Bar);

        // Test for convert2AlterIndex
        //assertEquals(23, moveCheck.convert2AlterIndex(1), "Should return 23 when i = 1");
        assertEquals(0, moveCheck.convert2AlterIndex(23), "Should return 0 when i = 23");
        assertEquals(1, moveCheck.convert2AlterIndex(22), "Should return 1 when i = 22");
    }

    //Test for homecheck
    @org.junit.jupiter.api.Test
    void testHomeCheck() {
        // Create mock data for Pips and Bar
        ArrayList<Pip> Pips = new ArrayList<>();
        ArrayList<Pip> Bar = new ArrayList<>();
        Bar.add(new Pip()); // Bar[0] for orientation == turn
        Bar.add(new Pip()); // Bar[1] for orientation != turn

        Turn turn = new Turn();
        turn.setTurn(true, true);  // Turn with matching orientation
        Move_Check moveCheck = new Move_Check(Pips, turn, Bar);

        // Test for homeCheck
        // Test when there are no checkers outside home area
        for (int i = 0; i < 24; i++) {
            Pips.add(new Pip());
        }
        assertTrue(moveCheck.homeCheck(), "Should return true when there are no checkers outside home area.");

        // Test when there are checkers outside home area
        Pips.get(0).addChecker(new Checker(true)); // Add a checker to the first pip
        assertFalse(moveCheck.homeCheck(), "Should return false when there are checkers outside home area.");
    }


    // Test the bar count - counts number of checkers on the bar
    @org.junit.jupiter.api.Test
    void testBarCount() {
        // Initialise Bar as an ArrayList of Pip objects
        ArrayList<Pip> Bar = new ArrayList<>();
        Bar.add(new Pip()); // Bar[0]
        Bar.add(new Pip()); // Bar[1]

        // Add checkers to Bar[0] (3 checkers)
        for (int i = 0; i < 3; i++) {
            Bar.get(0).addChecker(new Checker(true)); // Assuming addChecker method exists
        }

        // Initialise Pips as an ArrayList of Pip objects
        ArrayList<Pip> Pips = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            Pips.add(new Pip()); // Default initialization
        }

        // Test for Turn matching orientation
        Turn turn = new Turn();
        turn.setTurn(true, true); // Matching orientation
        Move_Check moveCheck = new Move_Check(Pips, turn, Bar);

        // Check bar count for matching turn
        assertEquals(3, moveCheck.barCount(), "Should return the count of checkers on Bar[0] for the current turn.");
    }

    //check if can move checker off the board
    // test for public boolean canMoveOff(int from_pip, int steps)
    @org.junit.jupiter.api.Test
    void testCanMoveOff() {
        // Create mock data for Pips and Bar
        ArrayList<Pip> Pips = new ArrayList<>();
        ArrayList<Pip> Bar = new ArrayList<>();
        Bar.add(new Pip()); // Bar[0] for orientation == turn
        Bar.add(new Pip()); // Bar[1] for orientation != turn

        Turn turn = new Turn();
        turn.setTurn(true, true);  // Turn with matching orientation
        Move_Check moveCheck = new Move_Check(Pips, turn, Bar);

        // Test for homeCheck
        // Test when there are no checkers outside home area
        for (int i = 0; i < 24; i++) {
            Pips.add(new Pip());
        }
        assertTrue(moveCheck.homeCheck(), "Should return true when there are no checkers outside home area.");

        // Test when there are checkers outside home area
        Pips.get(0).addChecker(new Checker(true)); // Add a checker to the first pip
        assertFalse(moveCheck.homeCheck(), "Should return false when there are checkers outside home area.");


        // Test Case 2: Invalid move off the board for white
        int from_pip = 0; // Second last position in the home area
        int steps = 2; // Two steps to move off the board
        assertFalse(moveCheck.canMoveOff(from_pip, steps), "Should return false for an invalid move off the board.");

    }


    // Test for canMove method
    ////checks if move can be made - returns index of destination pip
    // will return an index of -2 if cannot be done
    // Test for invalid moves is not tested in this method
    @org.junit.jupiter.api.Test
    void testCanMove() {
        // Initialise Pips
        ArrayList<Pip> Pips = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            Pips.add(new Pip()); // Default Pip
        }

        // Initialise Bar
        ArrayList<Pip> Bar = new ArrayList<>();
        Bar.add(new Pip()); // First Pip in Bar (for orientation == turn)
        Bar.add(new Pip()); // Last Pip in Bar (for orientation != turn)

        // Place checkers in specific positions
        Pips.get(0).addChecker(new Checker(true)); // Place a checker at position 0
        Pips.get(5).addChecker(new Checker(false)); // Block position 5
        Pips.get(10).addChecker(new Checker(true)); // Place another checker at position 10

        // Initialise Turn (white's turn with matching orientation)
        Turn turn = new Turn();
        turn.setTurn(true, true); // Matching orientation for white

        // Create Move_Check instance
        Move_Check moveCheck = new Move_Check(Pips, turn, Bar);

        // Test Case 1: Checker can move to an empty destination
        int from_pip = 0;
        int steps = 3; // Move to position 3
        assertEquals(3, moveCheck.canMove(from_pip, steps), "Should return destination index 3 for a valid move.");

        // Test Case 2: Checker cannot move to a blocked pip
        moveCheck.setBarFlag(true);
        moveCheck.updateBarFlag(); // Bar flag should be true

        from_pip = -1; // Checker on the bar
        steps = 2; // Move to position 2
        assertEquals(-2, moveCheck.canMove(from_pip, steps), "Checker cannot land on blocked pip."); // error message

    }

}