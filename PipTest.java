import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PipTest {
    // Test for Pip class
    // public isEmpty
    // public returnLength
    // public returnColour
    // public blockCheck
    // public hitCheck
    // public addChecker
    // public removeChecker
    // public Display

    //Test Case for empty pip (Test isEmpty, returnLength, returnColour)
    @org.junit.jupiter.api.Test
    void testEmptyPip() {

        // Test case for empty pip
        Pip emptyPip = new Pip();

        // Assert that the pip is empty
        assertTrue(emptyPip.isEmpty(), "A new Pip should be empty");

        // Assert that the length of the pip is 0
        assertEquals(0, emptyPip.returnLength(), "A new Pip should have a length of 0");

    }

    @org.junit.jupiter.api.Test
    void testAdd_remove_Checker() {
        // Test case for adding checker
        Pip addPip = new Pip();
        Checker addChecker = new Checker(true);
        addPip.addChecker(addChecker);

        // Assert that the checker has been added
        assertEquals(1, addPip.returnLength(), "Pip should have a length of 1");

        // Assert that the pip is no longer empty
        assertFalse(addPip.isEmpty(), "Pip should not be empty after adding a checker");

        // Assert that the length of the pip is 1
        assertEquals(1, addPip.returnLength(), "Pip should have a length of 1 after adding a checker");

        // Test case for removing checker
        Pip removePip = new Pip();
        Checker removeChecker = new Checker(false);
        removePip.addChecker(removeChecker);
        removePip.removeChecker();

        // Assert that the checker has been removed
        assertEquals(0, removePip.returnLength(), "Pip should have a length of 0");
    }

    //Check that checker can be removed from an empty pip
    @org.junit.jupiter.api.Test
    void testRemoveCheckerFromEmptyPip() {
        Pip pip = new Pip();

        // Attempt to remove a checker from an empty pip
        assertDoesNotThrow(pip::removeChecker, "Removing a checker is not possible from an empty pip");
    }

    @org.junit.jupiter.api.Test
    void testHitCheck() {
        // Test case for hit pip
        Pip hitPip = new Pip();
        Checker whiteChecker = new Checker(false);
        hitPip.addChecker(whiteChecker);

        // Assert that the pip is hit
        assertTrue(hitPip.hitCheck(true), "Pip should be hit");

        // Assert that the pip is not hit for white checkers
        assertFalse(hitPip.hitCheck(false), "Pip not be hit for checkers of the same colour");
    }

    @org.junit.jupiter.api.Test
    void testBlockCheck() {
        // Test case for blocked pip
        Pip blockedPip = new Pip();
        Checker blackChecker = new Checker(true);
        blockedPip.addChecker(blackChecker);

        // Assert that the pip is blocked
        assertFalse(blockedPip.blockCheck(true), "Pip should be blocked");

        // Assert that the pip is not blocked for white checkers
        assertFalse(blockedPip.blockCheck(false), "Pip should not be blocked for checkers of the same colour");
    }

    @org.junit.jupiter.api.Test
    void Display() {
        // Test case for displaying pip
        Pip displayPip = new Pip();
        Checker displayChecker = new Checker(true);
        displayPip.addChecker(displayChecker);

        // Assert that the display string is correct
        assertEquals("1B", displayPip.Display(), "Pip should display the correct string");
    }



}



