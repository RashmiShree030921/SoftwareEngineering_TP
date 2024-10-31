import java.util.Random;
import java.util.Scanner;

public class Player_IDs
{
    private String[] players = new String[2];
    private Scanner scan = new Scanner(System.in);

    private Random dice = new Random();
    private boolean first_player;

    public Player_IDs()
    {
        getPlayerNames();
        first_player = startRoll();
    }
    public boolean returnPlayerOne() { return first_player; }

    //return the name of player who's turn it is
    public String returnName(Boolean turn)
    {
        if(!turn)
            return players[0];
        return players[1];
    }

    //get player names
    public void getPlayerNames()
    {
        System.out.print("Enter name of Player 1 (White): ");
        this.players[0] = scan.nextLine();
        System.out.print("Enter name of Player 2 (Black): ");
        this.players[1] = scan.nextLine();
    }

    // do starting roll and find player 1
    public boolean startRoll()
    {
        System.out.println("\n\nBoth players must now roll a die each - the player who gets the highest roll will go first. ");
        int roll[] = {0,0};
        String input;

        do
        {
            System.out.println("Please enter 'X' to continue. ");
            input = scan.nextLine();
        }while(!input.matches("^([xX]{1})$"));

        while(roll[0] == roll[1])
        {
            roll[0] = dice.nextInt(6) + 1;
            roll[1] = dice.nextInt(6) + 1;
        }

        System.out.println("\n"+ players[0] + " has rolled " + roll[0] + ".");
        System.out.println(players[1] + " has rolled " + roll[1] + ".");

        if(roll[0] > roll[1])
        {
            System.out.println("\n" + players[0] + " goes first. \n");
            return false;
        }
        System.out.println("\n" + players[1] + " goes first. \n");
        return true;
    }

}
