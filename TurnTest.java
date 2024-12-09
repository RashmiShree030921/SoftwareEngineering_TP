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

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        turn = new Turn();
    }

    @org.junit.jupiter.api.Test
    void testSetTurn() {
        turn.setTurn(true, true);
        assertEquals(true, turn.returnTurn(), "Turn should match the input boolean value");
        assertEquals(true, turn.returnOrientation(), "Orientation should match the input boolean value");
    }

    @org.junit.jupiter.api.Test
    void testReturnColourForBlack() {
        turn.setTurn(true, true);
        assertEquals("B", turn.returnColour(), "Turn should return 'B' for black (true)");
    }

    @org.junit.jupiter.api.Test
    void testReturnColourForWhite() {
        turn.setTurn(false, false);
        assertEquals("W", turn.returnColour(), "Turn should return 'W' for white (false)");
    }

    @org.junit.jupiter.api.Test
    void testChangeTurn() {
        turn.setTurn(true, true);
        turn.changeTurn();
        assertEquals(false, turn.returnTurn(), "Turn should toggle to false after changeTurn");
        turn.changeTurn();
        assertEquals(true, turn.returnTurn(), "Turn should toggle back to true after another changeTurn");
    }

}
