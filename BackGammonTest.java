// Hannah Nolan gitHub ID (hnolan2019) ucd
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;

class BackGammonTest {

    // Methods to test in BackGammonTest.java
    // boardSetup
    // boardSituations
    // getPlayerNames()
    // gameloop()


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

    @Test
    void testBoardSituations() throws Exception {
        // Create an instance of BackGammon
        BackGammon game = new BackGammon();

        // Use Reflection to invoke the private boardSetup() method
        Method boardSetup = BackGammon.class.getDeclaredMethod("boardSetup");
        boardSetup.setAccessible(true);
        boardSetup.invoke(game);

        // Use Reflection to access private fields for assertions
        Field brownHomeField = BackGammon.class.getDeclaredField("brownHome");
        Field brownOuterField = BackGammon.class.getDeclaredField("brownOuter");
        Field whiteHomeField = BackGammon.class.getDeclaredField("whiteHome");
        Field whiteOuterField = BackGammon.class.getDeclaredField("whiteOuter");

        // Make private fields accessible
        brownHomeField.setAccessible(true);
        brownOuterField.setAccessible(true);
        whiteHomeField.setAccessible(true);
        whiteOuterField.setAccessible(true);

        // Get the actual field values
        String[] brownHome = (String[]) brownHomeField.get(game);
        String[] brownOuter = (String[]) brownOuterField.get(game);
        String[] whiteHome = (String[]) whiteHomeField.get(game);
        String[] whiteOuter = (String[]) whiteOuterField.get(game);

        // Add assertions to verify board state
        assertEquals("[ ]", brownHome[1], "Brown Home slot 1 should be empty");
        assertEquals("|W2|", brownHome[5], "Brown Home slot 5 should contain '|W2|'");

        assertEquals("|W5|", brownOuter[0], "Brown Outer slot 0 should contain '|W5|'");
        assertEquals("|B3|", brownOuter[4], "Brown Outer slot 4 should contain '|B3|'");

        assertEquals("|W5|", whiteHome[0], "White Home slot 0 should contain '|W5|'");
        assertEquals("|B2|", whiteHome[5], "White Home slot 5 should contain '|B2|'");

        assertEquals("|B5|", whiteOuter[0], "White Outer slot 0 should contain '|B5|'");
        assertEquals("|W3|", whiteOuter[4], "White Outer slot 4 should contain '|W3|'");
    }



}
