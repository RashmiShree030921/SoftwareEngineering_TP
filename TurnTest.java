import static org.junit.jupiter.api.Assertions.*;


class TurnTest {

    // Test for Turn class
    // public turn
    // public returnOrientation
    // public returnTurn
    // public returnColour
    // public changeTurn

    @org.junit.jupiter.api.Test
    void check() {

        // Test case for B  (true)
        boolean BlackColour = true;
        Turn BlackTurn = new Turn();
        BlackTurn.setTurn(BlackColour, BlackColour);

        // Assert that the turn color matches the input
        assertEquals(BlackColour, BlackTurn.returnTurn(), "Turn should match the input boolean value");

        // Assert that the orientation color matches the input
        assertEquals(BlackColour, BlackTurn.returnOrientation(), "Orientation should match the input boolean value");

        // Assert that the display string is "B" for brown
        assertEquals("B", BlackTurn.returnColour(), "Turn should return 'B' for black (true)");

        // Test case for white color (false)
        boolean whiteColour = false;
        Turn whiteTurn = new Turn();
        whiteTurn.setTurn(whiteColour, whiteColour);

        // Assert that the turn color matches the input
        assertEquals(whiteColour, whiteTurn.returnTurn(), "Turn should match the input boolean value");

        // Assert that the orientation color matches the input
        assertEquals(whiteColour, whiteTurn.returnOrientation(), "Orientation should match the input boolean value");

        // Assert that the display string is "W" for white
        assertEquals("W", whiteTurn.returnColour(), "Turn should return 'W' for white (false)");

        // Test case for changing turn
        BlackTurn.changeTurn();

        // Assert that the turn color has changed
        assertEquals(whiteColour, BlackTurn.returnTurn(), "Turn should change to the opposite of the input boolean value");

        // Test case for changing turn
        whiteTurn.changeTurn();

        // Assert that the turn color has changed
        assertEquals(BlackColour, whiteTurn.returnTurn(), "Turn should change to the opposite of the input boolean value");
    }

}