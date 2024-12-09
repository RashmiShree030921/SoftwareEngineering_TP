// Hannah Nolan gitHub ID (hnolan2019) ucd
import java.util.ArrayList;

public class PrintBoard {
    private Player_IDs player;

    // Constructor to set the player ID
    public PrintBoard(Player_IDs player) {
        this.player = player;
    }

    // use ArrayList
    public void print(ArrayList<Pip> pips, Turn turn,  ArrayList<Pip> bar)
    {
        int totalWidth = 46;                                    // Assume the total width of the game board
        System.out.print("\033[H\033[2J");                      // Clear the console
        System.out.flush();                                         // Flush the output stream
        String title = "Backgammon";                            // Title of the game
        printHeader(title, totalWidth);                         // Print the game header
        PrintCurrentPlayerName(turn.returnTurn(), totalWidth);               // Print the current player's name
        printBoardLayout(pips, turn, bar);          // Print the board layout
    }

    // Method to print the current player's name
    public void PrintCurrentPlayerName(boolean turn, int totalWidth) {
        System.out.println("=============================================");
        String title = "Current Player: " + player.returnName(turn);
        int padding = (totalWidth - title.length()) / 2;
        System.out.println(" ".repeat(padding) + title);
        System.out.println("=============================================");
    }

    //filf indicies for white player turn
    private int flipIndex(int index) {
        if (index >= 0 && index < 12) {
            return 23 - index; // Flip bottom row to top row
        } else if (index >= 12 && index < 24) {
            return 23 - index; // Flip top row to bottom row
        }
        return index; // Return
    }


    // BOARD layout
    public void printBoardLayout(ArrayList<Pip> pips, Turn turn, ArrayList<Pip> bar)
    {
        StringBuilder numBorderTop = new StringBuilder("   ");
        StringBuilder numBorderBottom = new StringBuilder("  ");
        String[] checkers = new String[24];

        boolean turn_check = turn.returnTurn();

        //Take this and check return turn: (if turn is true == black player, else while == white player)


        // Organize the board numbers and checker positions based on the turn orientation
        if (turn_check) // orientation for player with black checkers
        {
            // Organise the top numbers (12-23)
            for (int i = 12; i < 24; i++) {
                numBorderTop.append(String.format("%2d ", i));
                if (i == 17) {
                    numBorderTop.append("    "); // Gap for the bar
                }
                checkers[i] = pips.get(i).Display();
            }

            // Organize the bottom numbers (0-11)
            for (int i = 11; i >= 0; i--) {
                if (i == 5) {
                    numBorderBottom.append("     "); // Gap for the bar
                }
                numBorderBottom.append(String.format("%2d ", i));
                checkers[i] = pips.get(i).Display();
            }
        }
        else
        { // If it's player 2's turn (flipped orientation)
            // Organize the top numbers (11-0)
            for (int i = 11; i >= 0; i--) {
                int flippedIndex = flipIndex(i);
                if (i == 5) {
                    numBorderTop.append("    "); // Gap for the bar
                }
                numBorderTop.append(String.format("%2d ", i));
                checkers[i] = pips.get(flippedIndex).Display();
            }

            // Organize the bottom numbers (12-23)
            for (int i = 12; i < 24; i++) {
                int flippedIndex = flipIndex(i);
                numBorderBottom.append(String.format("%2d ", i));
                if (i == 17) {
                    numBorderBottom.append("     "); // Gap for the bar
                }
                checkers[i] = pips.get(flippedIndex).Display();
            }
        }

        // Print the top border numbers
        System.out.println(numBorderTop);

        // Print the board layout with the checkers
        System.out.println("\u001B[32m====================|===|====================\u001B[0m");

        // Print the top half of the board (pips 12-23 or flipped)
        for (int row = 5; row > 0; row--)
        {
            System.out.print("\u001B[32m| \u001B[0m");
            for (int i = 12; i < 18; i++)
            {
                printPipRow(checkers[i], row);
            }
            int test = 0;
            printBar(bar, test, row - 1); // Adjusts for white checkers

            for (int i = 18; i < 24; i++)
            {
                printPipRow(checkers[i], row);
            }
            System.out.println("\u001B[32m|\u001B[0m");
        }

        // Print the bar row
        System.out.print("\u001B[32m|\u001B[0m                   \u001B[32m|\u001B[0mBAR\u001B[32m|\u001B[0m                  \u001B[32m|\u001B[0m \n");

        // Print the bottom half of the board (pips 0-11 or flipped)
        for (int row = 1; row <= 5; row++) {
            System.out.print("\u001B[32m| \u001B[0m");
            for (int i = 11; i >= 6; i--) {
                printPipRow(checkers[i], row);
            }


            int test = 1;
            printBar(bar, test, row - 1); // Adjusts for black checkers

            for (int i = 5; i >= 0; i--) {
                printPipRow(checkers[i], row);
            }
            System.out.println("\u001B[32m|\u001B[0m");
        }

        // Print the bottom border numbers
        System.out.println("\u001B[32m====================|===|====================\u001B[0m");

        System.out.println(numBorderBottom);
    }



    // Method to print a row of checkers for a pip
    private void printPipRow(String checkerDisplay, int row) {

        if (checkerDisplay == null || checkerDisplay.length() < 2) {
            System.out.print(" | ");
            return;
        }

        int checkerCount;  // Count checkers
        try {
            checkerCount = Integer.parseInt(checkerDisplay.substring(0, 1));  // Get checker count
        } catch (NumberFormatException e) {
            checkerCount = 0; // Default to 0 if error occurs
        }
        String checkerColor = checkerDisplay.substring(1, 2); // Default to space if colour missing

        if ("W".equals(checkerColor)) {
            checkerColor = "\u25CF"; // White Circle
        } else if ("B".equals(checkerColor)) {
            checkerColor = "\u25CB"; // Black Circle with White Outline
        } else {
            checkerColor = " "; // Default to space if no color
        }

        // Print checker color if exists for this row, otherwise print spaces
        if (checkerCount >= row) {
            System.out.print(" " + checkerColor + " ");
        } else {
            System.out.print(" | ");  // 3-character space
        }
    }


    // Print the game Header
    public void printHeader(String title, int totalWidth) {
        int padding = (totalWidth - title.length()) / 2;
        System.out.println(" ".repeat(padding) + title);
    }

    private void printBar(ArrayList<Pip> bar, int test, int index) {
        // Check counts for each player's bar
        int count = 0;
        // Assuming bar.get(0) is Player 1 (White) and bar.get(1) is Player 2 (Black)
        if (!bar.isEmpty() && test < bar.size() && !bar.get(test).isEmpty()) {
            count = bar.get(test).returnLength(); // Count of checkers
        }

        String[] checkers = new String[5]; // Array for displaying up to 5 checkers (max per pip)

        // Populate the `checkers` array with individual checker symbols or spaces if empty
        for (int i = 0; i < 5; i++) {
            if (i < count) {
                // Get the colour of the checker
                checkers[i] = bar.get(test).Display().substring(1, 2);
                checkers[i] = " " + checkers[i] + " "; // Add spaces around the checker;
            } else {
                checkers[i] = " | "; // Fill empty spots with spaces
            }
        }

        // Print the requested checker at the specified index with formatting
        if (index >= 0 && index < checkers.length) {
            System.out.printf("\u001B[32m|\u001B[0m%-3s\u001B[32m|\u001B[0m", checkers[index]); // Left-align with width 3
        }
    }


}
