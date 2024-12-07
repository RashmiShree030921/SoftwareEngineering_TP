import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    private ArrayList<Pip> Pips = new ArrayList<>();
    private ArrayList<Pip> Bar = new ArrayList<>(2);
    private final int num_pips = 24;
    public final Player_IDs IDs;
    private Turn turn = new Turn();
    private ArrayList<Integer> rolls = new ArrayList<>();
    private Dice dice = new Dice(rolls);
    private Movement movement;
    private Command_Generator genCommands;
    public int matchLength;
    public int[] matchScore = {0, 0};
    public boolean doubleCubeNeutral = true; // Indicates if the double cube is in a neutral state
    public boolean doubleCubeOwner; // Indicates the owner of the double cube, false for player1, true for player2


    public Board() {
        this.Bar.add(new Pip());
        this.Bar.add(new Pip());
        generateBoard();
        movement = new Movement(dice, Pips, turn, Bar, this); // Pass 'this' to the Movement constructor
        IDs = new Player_IDs(turn, dice, this); // Pass 'this' to the Player_IDs constructor
        //setMatchLength(); // Make sure to call setMatchLength to initialize match length
    }

    public void generateBoard() {
        int[][] start_matrix = {{0, 5, 7, 11}, {2, 5, 3, 5}};
        boolean[][] start_colours = {{false, true, true, false}, {true, false, false, true}};
        int offset = 0;
        if (turn.returnOrientation()) offset = 1;
        for (int i = 0; i < num_pips; i++) Pips.add(new Pip());
        for (int j = 0; j < 2; j++)
            for (int i = 0; i < start_matrix[0].length; i++)
                for (int k = 0; k < start_matrix[1][i]; k++) {
                    int index = (int) (Math.pow(-1, j) * (start_matrix[0][i]) + (j * (num_pips - 1)));
                    Pips.get(index).addChecker(new Checker(start_colours[(j + offset) % 2][i]));
                }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Game started"); // Debug statement
        while (!movement.checkWin()) {
            displayMatchInfo(); // Display match info on each turn
            //displayDoubleOwnership(); // Display double ownership on each turn
            // Processing commands
            boolean proceed = false;
            while (!proceed) {
                System.out.print("Enter command (or type 'proceed' to move to the next turn): ");
                String command = scanner.nextLine().trim();
                if (command.equalsIgnoreCase("proceed")) {
                    // Proceed with the normal game move.
                    movement.moveChecker();
                    proceed = true;
                } else if (movement.processCommand(command)) {
                    // Command processed successfully.
                    proceed = true;
                } else {
                    System.out.println("Invalid command. Please try again.");
                }
            }
        }
        int gameScore = movement.calculateScore();
        updateMatchScore(gameScore);
        announceGameResult(gameScore);
        if (matchScore[0] < matchLength && matchScore[1] < matchLength) {
            System.out.println("Starting next game...");
            resetBoard();
            play();
        } else {
            System.out.println("Match over! " + (matchScore[0] >= matchLength ? IDs.returnName(false) : IDs.returnName(true)) + " wins the match!");
        }
    }


    public void resetBoard() {
        Pips.clear();
        Bar.clear();
        Bar.add(new Pip());
        Bar.add(new Pip());
        generateBoard();
    }

    public void setMatchLength() {
        System.out.print("Enter the length of the match: ");
        Scanner scanner = new Scanner(System.in);
        matchLength = scanner.nextInt();
        scanner.nextLine();  // Consume newline left-over
    }

    public void updateMatchScore(int gameScore) {
        if (turn.returnTurn()) matchScore[1] += gameScore;
        else matchScore[0] += gameScore;
        System.out.println("Current Match Score: " + IDs.returnName(false) + " " + matchScore[0] + " - " + IDs.returnName(true) + " " + matchScore[1]);
    }

    public void announceGameResult(int gameScore) {
        String resultMessage;
        if (gameScore == 3) {
            resultMessage = "Game ends in a Backgammon! " + IDs.returnName(turn.returnTurn()) + " wins 3 points.";
        } else if (gameScore == 2) {
            resultMessage = "Game ends in a Gammon! " + IDs.returnName(turn.returnTurn()) + " wins 2 points.";
        } else {
            resultMessage = "Game ends in a Single! " + IDs.returnName(turn.returnTurn()) + " wins 1 point.";
        }
        System.out.println(resultMessage);
    }

    public void testCommands(String filename) {
        // Implement reading commands from a file and executing them
    }

    public void displayMatchInfo() {
        System.out.println("Match Length: " + matchLength);
        System.out.println("Current Match Score: " + IDs.returnName(false) + " " + matchScore[0] + " - " + IDs.returnName(true) + " " + matchScore[1]);
    }

    public void displayDoubleOwnership() {
        if (doubleCubeNeutral) {
            System.out.println("Double ownership: Neutral");
        } else {
            System.out.println("Double ownership: " + (doubleCubeOwner ? IDs.returnName(true) : IDs.returnName(false)));
        }
    }


}




