// hannah nolan gitbuh (hnolan2019)
import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    //Command class tests---------- is it fininshed
    // Test commandString               yes
    // Test returnStart                 yes
    // Test returnEnd                   yes
    // Test isEqual                     yes

    //Test commandString
    @org.junit.jupiter.api.Test
    void testCommandString() {
        // Test case for commandString
        Command command = new Command(1, 2);

        // Assert that the commandString is correct
        assertEquals(" 1-2 ", command.commandString(), "Command should be 1-2");
    }

    //Test returnStart
    @org.junit.jupiter.api.Test
    void testReturnStart() {
        // Test case for returnStart
        Command command = new Command(1, 2);

        // Assert that the start pip is correct
        assertEquals(1, command.returnStart(), "Start pip should be 1");
    }

    //Test returnEnd
    @org.junit.jupiter.api.Test
    void testReturnEnd() {
        // Test case for returnEnd
        Command command = new Command(1, 2);

        // Assert that the end pip is correct
        assertEquals(2, command.returnEnd(), "End pip should be 2");
    }

    //Test isEqual
    @org.junit.jupiter.api.Test
    void testIsEqual() {
        // Test case for isEqual
        Command command1 = new Command(1, 2);
        Command command2 = new Command(1, 2);

        // Assert that the commands are equal
        assertTrue(command1.isEqual(command2), "Commands should be equal");
    }

}

