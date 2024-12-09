// Hannah Nolan gitHub ID (hnolan2019) ucd
import static org.junit.jupiter.api.Assertions.*;


class TurnTest {

        //Methods to test in TurnTest.java-------| is method testsed |-------
        // setTurn()                                    yes
        //changeTurn()                                  yes
        //returnTurn()                                  yes
    //returnOrientation()                              yes

    //Test for setTurn
    Turn turn;

    //set up before each test
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        turn = new Turn();
    }

    // Test for setTurn method  - test if the turn and orientation are set correctly
    @org.junit.jupiter.api.Test
    void testSetTurn() {
        turn.setTurn(true, true);
        assertEquals(true, turn.returnTurn(), "Turn should match the input boolean value");
        assertEquals(true, turn.returnOrientation(), "Orientation should match the input boolean value");
    }

    // Test for returnTurn method - test if the correct turn is returned
    @org.junit.jupiter.api.Test
    void testReturnColourForBlack() {
        turn.setTurn(true, true);
        assertEquals("B", turn.returnColour(), "Turn should return 'B' for black (true)");
    }


    // Test for returnTurn method - test if the correct turn is returned
    @org.junit.jupiter.api.Test
    void testReturnColourForWhite() {
        turn.setTurn(false, false);
        assertEquals("W", turn.returnColour(), "Turn should return 'W' for white (false)");
    }

    // Test for returnOrientation method - test if the correct orientation is returned
    @org.junit.jupiter.api.Test
    void testChangeTurn() {
        turn.setTurn(true, true);
        turn.changeTurn();
        assertEquals(false, turn.returnTurn(), "Turn should toggle to false after changeTurn");
        turn.changeTurn();
        assertEquals(true, turn.returnTurn(), "Turn should toggle back to true after another changeTurn");
    }

}