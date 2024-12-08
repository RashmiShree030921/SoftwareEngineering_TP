import static org.junit.jupiter.api.Assertions.*;
class CheckerTest {

    @org.junit.jupiter.api.Test
    void check() {

        //To test in CheckerTest.java
        //Checker checker = new Checker(Colour.B);
        //assertEquals(Colour.B, checker.getCheckerColour());
        // assertEquals("B", checker.getCheckerDisplay());

        // Test case for brown color (true)
        boolean BlackColour = true;
        Checker BlackChecker = new Checker(BlackColour);

        // Assert that the checker color matches the input
        assertEquals(BlackColour, BlackChecker.returnColour(), "Checker should match the input boolean value");

        // Assert that the display string is "B" for brown
        assertEquals("B", BlackChecker.returnString(), "Checker should return 'B' for black (true)");

        // Test case for white color (false)
        boolean whiteColour = false;
        Checker whiteChecker = new Checker(whiteColour);

        // Assert that the checker color matches the input
        assertEquals(whiteColour, whiteChecker.returnColour(), "Checker should match the input boolean value");

        // Assert that the display string is "W" for white
        assertEquals("W", whiteChecker.returnString(), "Checker display should return 'W' for white (false)");
    }
}
