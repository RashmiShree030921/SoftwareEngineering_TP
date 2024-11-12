import java.util.Random;
import java.util.Scanner;

public class BackGammon
{
    private String[] brownHome;
    private String[] brownOuter;
    private String[] whiteHome;
    private String[] whiteOuter;

    private String player1_name;
    private String player2_name;

    private final Random roll = new Random();
    private final Scanner scanner = new Scanner(System.in);

    //------------------------------------------------///
    private void boardSetup() {
        brownHome = new String[6];
        brownOuter = new String[6];
        whiteHome = new String[6];
        whiteOuter = new String[6];

        //Empty slots are represented by [ ]
        for (int i = 0; i < 6; i++) {
            brownHome[i] = "[ ]";
            brownOuter[i] = "[ ]";
            whiteHome[i] = "[ ]";
            whiteOuter[i] = "[ ]";
        }

        // Setting initial checker positions
        brownHome[0] = "|B5|";
        brownHome[5] = "|W2|";
        brownOuter[0] = "|W5|";
        brownOuter[4] = "|B3|";
        whiteHome[0] = "|W5|";
        whiteHome[5] = "|B2|";
        whiteOuter[0] = "|B5|";
        whiteOuter[4] = "|W3|";

    }

    private void board_display()
    {
        System.out.println("\n ----- Backgammon Board -----");
        System.out.println("Player 1 Checkers on top; Player 2 Checkers on bottom\n");

        //---------------------------------//
        System.out.println("Brown Home:");
        for (int i = 0; i < 6; i++){
            System.out.print(brownHome[i] + " ");
        }
        System.out.println();

        //---------------------------------//
        System.out.println("Brown Outer:");
        for (int i = 0; i < 6; i++){
            System.out.print(brownOuter[i] + " ");
        }
        System.out.println();

        //---------------------------------//
        System.out.println("White Outer:");
        for (int i = 0; i < 6; i++){
            System.out.print(whiteOuter[i] + " ");
        }
        System.out.println();

        //---------------------------------//
        System.out.println("White Home:");
        for (int i = 0; i < 6; i++){
            System.out.print(whiteHome[i] + " ");
        }
        System.out.println();

    }

    private void getPlayerNames()
    {
        System.out.print("Enter name of Player 1 (White): ");
        player1_name = scanner.nextLine();
        System.out.print("Enter name of Player 2 (Black): ");
        player2_name = scanner.nextLine();
    }


    private void gameLoop()
    {
        boolean isGameOver = false;
        boolean isPlayer1Turn = true;

        while(!isGameOver){
            String currentPlayer = isPlayer1Turn ? player1_name : player2_name;
            System.out.println("\n" + currentPlayer + ", it's your turn! Type 'roll' to roll the dice or 'quit' to end the game.");

            String command = scanner.nextLine().toLowerCase();
            switch (command) {
                case "roll":
                    rollDice(currentPlayer);
                    isPlayer1Turn = !isPlayer1Turn;
                    break;

                case "quit":
                    System.out.println(currentPlayer + " has quit the game. Game over! ");
                    isGameOver = true;
                    break;

                default:
                    System.out.println("Invalid command. Please type 'roll' to roll the dice or 'quit' to end the game.");
                    break;
            }
        }
        scanner.close();
    }

    //Roll two dice  and display results
    private void rollDice(String player)
    {
        int dice1 = roll.nextInt(6) + 1;
        int dice2 = roll.nextInt(6) + 1;
        System.out.println(player + "rolled: " + dice1 + " and " + dice2);
    }

}
