// Hannah Nolan gitHub ID (hnolan2019) ucd
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;


// to implement Reflection
// www.geeksforgeeks.org/how-to-access-private-field-and-method-using-reflection-in-java/
// was used as a reference

class BackGammonTest {

    // Methods to test in BackGammonTest.java
    // boardSetup
    // boardSituations
    // getPlayerNames()
    // gameloop()


    // test that the board is setup correctly
    @Test
    void testBoardSetup() throws Exception {
        BackGammon game = new BackGammon();

        // Access private method using reflection
        Method boardSetup = BackGammon.class.getDeclaredMethod("boardSetup");
        boardSetup.setAccessible(true);
        boardSetup.invoke(game);

        // Use reflection again to verify board state
        Method boardDisplay = BackGammon.class.getDeclaredMethod("board_display");
        boardDisplay.setAccessible(true);
        boardDisplay.invoke(game);

    }

    // test the board situations
    @Test
    void testBoardSituations() throws Exception {
        // Create an instance of BackGammon
        BackGammon game = new BackGammon(); //instance

        // Use Reflection to invoke the private boardSetup() method
        Method boardSetup = BackGammon.class.getDeclaredMethod("boardSetup");
        boardSetup.setAccessible(true);
        boardSetup.invoke(game);

        // Use Reflection to access private fields for assertions
        Field brownHomeField = BackGammon.class.getDeclaredField("brownHome");  //state fields we want to access
        Field brownOuterField = BackGammon.class.getDeclaredField("brownOuter");
        Field whiteHomeField = BackGammon.class.getDeclaredField("whiteHome");
        Field whiteOuterField = BackGammon.class.getDeclaredField("whiteOuter");

        // set files to accessible
        brownHomeField.setAccessible(true);
        brownOuterField.setAccessible(true);
        whiteHomeField.setAccessible(true);
        whiteOuterField.setAccessible(true);

        // Get the actual field values
        String[] brownHome = (String[]) brownHomeField.get(game);
        String[] brownOuter = (String[]) brownOuterField.get(game);
        String[] whiteHome = (String[]) whiteHomeField.get(game);
        String[] whiteOuter = (String[]) whiteOuterField.get(game);

        //Brown home
        //   --------5 ------4 ------3 ---=----2 ----1 -----0
        //---------filled--empty--empty----empty----empty--filled
        //|W2| [ ] [ ] [ ] [ ] [ ] |W5|

        // Test (1): CHeck that pip 1 in brown home is empty and pip 5 contains "|W2|"
        assertEquals("[ ]", brownHome[1], "Brown Home pip 1 should be empty");
        assertEquals("|W2|", brownHome[5], "Brown Home pip 5 should contain '|W2|'");

        // Test (2) Check that pip 0 in brown outer contains "|W5|" and pip 4 contains "|B3|"
        assertEquals("|W5|", brownOuter[0], "Brown Outer pip 0 should contain '|W5|'");
        assertEquals("|B3|", brownOuter[4], "Brown Outer pip 4 should contain '|B3|'");

        // Test (3) Check that pip 0 in white home contains "|W5|" and pip 5 contains "|B2|"
        assertEquals("|W5|", whiteHome[0], "White Home pip 0 should contain '|W5|'");
        assertEquals("|B2|", whiteHome[5], "White Home pip 5 should contain '|B2|'");

        // Test (4) Check that pip 0 in white outer contains "|B5|" and pip 4 contains "|W3|"
        assertEquals("|B5|", whiteOuter[0], "White Outer pip 0 should contain '|B5|'");
        assertEquals("|W3|", whiteOuter[4], "White Outer pip 4 should contain '|W3|'");
    }



}
