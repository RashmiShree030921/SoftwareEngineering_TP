import java.util.ArrayList;

public class PrintBoard {
    private Player_IDs player;

    // Constructor to set the player ID
    public PrintBoard(Player_IDs player) {
        this.player = player;
    }

    // Updated to use ArrayList instead of Vector
    public void print(ArrayList<Pip> pips, boolean turn, ArrayList<Pip> bar) {
        int totalWidth = 46;                        // Assume the total width of the game board
        System.out.print("\033[H\033[2J");          // Clear the console
        System.out.flush();                         // Flush the output stream
        String title = "Backgammon";                // Title of the game
        printHeader(title, totalWidth);             // Print the game header
        PrintCurrentPlayerName(turn, totalWidth);   // Print the current player's name
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

    // BOARD layout
    public void printBoardLayout(ArrayList<Pip> pips, boolean turn, ArrayList<Pip> bar) {
        StringBuilder numBorderTop = new StringBuilder("    ");
        StringBuilder numBorderBottom = new StringBuilder("   ");
        String[] checkers = new String[24];

        // Check if the board is oriented correctly
        if (turn){
            // Organising the top numbers (12-23)
            for (int i = 12; i < 24; i++) {
                numBorderTop.append(String.format("%2d ", i)); // Fixed width for all numbers

                //gap added after 6th pip for the bar
                if (i == 17) {
                    numBorderTop.append("    ");
                }
                checkers[i] = pips.get(i).Display();
            }

            // Organising the bottom numbers (0-11)
            for (int i = 11; i >= 0; i--) {
                if (i == 5) {
                    numBorderBottom.append("    "); // Corrected to numBorderBottom
                }
                numBorderBottom.append(String.format("%2d ", i)); // Fixed width for numbers
                checkers[i] = pips.get(i).Display();
            }
        } else {
            // Organizing the top numbers (12-23)
            for (int i = 11; i >= 0 ; i--) {
                if (i == 6) {
                    numBorderTop.append("    "); // Corrected to numBorderTop
                }
                numBorderTop.append(String.format("%2d ", i)); // Fixed width for all numbers
                checkers[i] = pips.get(i).Display();
            }

            // Organizing the bottom numbers (0-11)
            for (int i = 12; i <= 23; i++) {
                numBorderBottom.append(String.format("%2d ", i)); // Fixed width for numbers
                checkers[i] = pips.get(i).Display();
                if (i == 17) {
                    numBorderBottom.append("    ");
                }
            }
        }


        System.out.println(numBorderTop);
        System.out.print(""" 
                 __________________________________________\s
                |                   |   |                  |
                """);

        // Print the top half of the board (pips 12-23)
        for (int row = 5; row > 0; row--) {   // Print each row of checkers for the top half of the board
            System.out.print("| ");
            for (int i = 12; i < 18; i++) {
                printPipRow(checkers[i], row);
            }
            int test = 0;
            //System.out.print("|   | ");
            printBar(bar, test, row - 1);

            for (int i = 18; i < 24; i++) {
                printPipRow(checkers[i], row);
            }
            System.out.println("|");
        }

        // Print the bar row
        System.out.print("|                   |BAR|                  |\n");


        // Print the bottom half of the board (pips 0-11)
        for (int row = 1; row <= 5; row++) {
            System.out.print("| ");
            for (int i = 11; i >= 6; i--) {
                printPipRow(checkers[i], row);
            }
            //System.out.print("|   | ");
            int test = 1;
            printBar(bar, test, row - 1);
            for (int i = 5; i >= 0; i--) {
                printPipRow(checkers[i], row);
            }
            System.out.println("|");
        }

        System.out.println("|___________________|___|__________________|");
        System.out.println(numBorderBottom);
    }

    // Method to print a row of checkers for a pip
    private void printPipRow(String checkerDisplay, int row) {
        if (checkerDisplay == null || checkerDisplay.length() < 2) {
            System.out.print("   ");
            return;
        }

        int checkerCount;  // Count checkers
        try {
            checkerCount = Integer.parseInt(checkerDisplay.substring(0, 1));  // Get checker count
        } catch (NumberFormatException e) {
            checkerCount = 0; // Default to 0 if error occurs
        }
        String checkerColor = checkerDisplay.substring(1, 2); // Default to space if color missing

        // Print checker color if exists for this row, otherwise print spaces
        if (checkerCount >= row) {
            System.out.print(" " + checkerColor + " ");
        } else {
            System.out.print("   ");  // 3-character space
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

        String[] checkers = new String[5]; // Array for displaying up to 5 checkers

        // Populate the `checkers` array with individual checker symbols or spaces if empty
        for (int i = 0; i < 5; i++) {
            if (i < count) {
                // Get the color of the checker (e.g., "w" or "b")
                checkers[i] = bar.get(test).Display().substring(1, 2); // Assuming `Display()` returns something like "5w"
            } else {
                checkers[i] = "   "; // Fill empty spots with spaces
            }
        }

        // Print the requested checker at the specified index with formatting
        if (index >= 0 && index < checkers.length) {
            System.out.printf("|%-3s|", checkers[index]); // Left-align with width 3
        }
    }









}
