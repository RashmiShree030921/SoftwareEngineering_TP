import java.util.Scanner;

public class Player_IDs {
    private String player1Name;
    private String player2Name;
    private Turn turn;
    private Dice dice;
    private Board board;

    public Player_IDs(Turn turn, Dice dice, Board board) {
        this.turn = turn;
        this.dice = dice;
        this.board = board;
        askPlayerNames();
        board.setMatchLength(); // Set match length after setting player names
    }

    public void askPlayerNames() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name for Player 1: ");
        player1Name = scanner.nextLine();
        System.out.print("Enter name for Player 2: ");
        player2Name = scanner.nextLine();
    }

    public String returnName(boolean player) {
        return player ? player2Name : player1Name;
    }

    public void setPlayer1Name(String name) {
        this.player1Name = name;
    }

    public void setPlayer2Name(String name) {
        this.player2Name = name;
    }
}
