// Hannah Nolan gitHub ID (hnolan2019) ucd
import static org.junit.jupiter.api.Assertions.*;
class CheckerTest {

    @org.junit.jupiter.api.Test
    void check() {

        //To test in CheckerTest.java                                       is it tested
        //Checker checker = new Checker(Colour.B);
        //returnColour                                                          yes
        // returnString;                                                        yes

        // Test case for black colour - (true)
        boolean BlackColour = true; // set test boolean to true
        Checker BlackChecker = new Checker(BlackColour); // create checker object from test boolean

        // Assert that the checker colour matches the input - tests return colour method (true)
        assertEquals(BlackColour, BlackChecker.returnColour(), "Checker should match the input boolean value");

        // Assert that the display string is "B" for blaack - tests return string method
        assertEquals("B", BlackChecker.returnString(), "Checker should return 'B' for black (true)");

        // Test case for white color (false) - invalid
        assertNotEquals(false, BlackChecker.returnColour(),"Checker should match the input boolean value");

        // Test case for white color (false)
        boolean whiteColour = false;
        Checker whiteChecker = new Checker(whiteColour);

        // Assert that the checker colour matches the input - tests return colour method
        assertEquals(whiteColour, whiteChecker.returnColour(), "Checker should match the input boolean value");

        // Assert that the display string is "W" for white -    tests return string method
        assertEquals("W", whiteChecker.returnString(), "Checker display should return 'W' for white (false)");
    }
}
