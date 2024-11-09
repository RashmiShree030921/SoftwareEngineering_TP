/*
    * Class to print the board layout
    * More to be added
    *   does not inlcude the dice roll/ Score
 */

import java.util.Vector;

public class PrintBoard {
    Player_IDs player;

    // Constructor to set the player ID
    public PrintBoard(Player_IDs player) {
        this.player = player;
    }

    public void print(Vector<Pip> pips, boolean orientation, boolean turn) {
        int totalWidth = 46;                        // Assume the total width of the game board
        System.out.print("\033[H\033[2J");          // Clear the console
        System.out.flush();                         // Flush the output stream

        String title = "Backgammon";                // Title of the game
        printHeader(title, totalWidth);             // Print the game header
        PrintCurrentPlayerName(turn, totalWidth);   // Print the current player's name
        printBoardLayout(pips);                     // Print the board layout
        printGameNumber(Board.gameCount);           // Print the game number
    }

    // Method to print the current player's name
    public void PrintCurrentPlayerName(boolean turn, int totalWidth) {
        System.out.println("=============================================");
        String title = "Current Player: " + player.returnName(turn);
        int padding = (totalWidth - title.length()) / 2;
        System.out.println(" ".repeat(padding) + title );
        System.out.println("=============================================");
    }

    // Method to print only the board layout
    public void printBoardLayout(Vector<Pip> pips) {
        StringBuilder numBorderTop = new StringBuilder("    ");
        StringBuilder numBorderBottom = new StringBuilder("   ");
        String[] checkers = new String[24];

        // Orientation: indices 13 to 24 on top row, 12 to 1 on bottom row (0 index accounted for)
        //Organising the rop numbers
        for (int i = 12; i < 24; i++) {
            numBorderTop.append(String.format("%2d ", i + 1)); // Fixed width for all numbers

            //gap added after 6th pip for the bar
            if (i == 17) {
                numBorderTop.append("    ");
            }
            checkers[i] = pips.get(i).Display();
        }

        //Organising the bottom numbers
        for (int i = 11; i >= 0; i--) {
            numBorderBottom.append(String.format("%2d ", i + 1)); // Fixed width for numbers
            if (i == 6) {
                numBorderBottom.append("    ");
            }
            checkers[i] = pips.get(i).Display();
        }
        //-------------------------Print top Row number border-----------------------------//
        System.out.println(numBorderTop);
        // Print the board layout
        System.out.print("""
                 ___________________________________________\s
                |                   |   |                   |
                """);

        for (int row = 5; row > 0; row--) {   // Print each row of checkers for the top half
            System.out.print("| ");
            for (int i = 12; i < 18; i++) {
                printPipRow(checkers[i], row);
            }
            System.out.print("|   | ");
            for (int i = 18; i < 24; i++) {
                printPipRow(checkers[i], row);
            }
            System.out.println("|");
        }

        // Print the middle row of the board
        System.out.print("|                   |BAR|                   |\n");

        // -------------------------Print bottom Row number border-----------------------------//
        for (int row = 1; row <= 5; row++) {
            System.out.print("| ");
            for (int i = 11; i >= 6; i--) {
                printPipRow(checkers[i], row);
            }
            System.out.print("|   | ");
            for (int i = 5; i >= 0; i--) {
                printPipRow(checkers[i], row);
            }
            System.out.println("|");
        }

        // Print bottom number border
        System.out.println("|___________________|___|___________________|");
        System.out.println(numBorderBottom);
    }

    // Method to print a row of checkers for a pip
    private void printPipRow(String checkerDisplay, int row) {
        if (checkerDisplay == null || checkerDisplay.length() < 2) {
            System.out.print("   ");  // 3-character space for empty pips
            return;
        }

        int checkerCount;  //Count checkers
        try {
            checkerCount = Integer.parseInt(checkerDisplay.substring(0, 1));  // Get checker count
        } catch (NumberFormatException e) {
            checkerCount = 0; // Default to 0 if error occurs
        }
        String checkerColor = checkerDisplay.substring(1, 2); // Default to space if colour missing

        // Print checker color if exists for this row, otherwise print spaces
        if (checkerCount >= row) {
            System.out.print(" " + checkerColor + " ");
        } else {
            System.out.print("   ");  //3-character space
        }
    }

    //Print the game Header
    public void printHeader(String title, int totalWidth) {
        int padding = (totalWidth - title.length()) / 2;
        System.out.println(" ".repeat(padding) + title );
    }

    //Game Counter Method, Game count will be calculated in the Board class and incremented after each game
    // get gameNumber from the board class
    public static void printGameNumber(int gameNumber) {
        System.out.println("Game Number: " + gameNumber);
    }
}
