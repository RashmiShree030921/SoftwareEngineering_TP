import java.util.ArrayList;
import java.util.Scanner;

public class Player_IDs
{
    private String[] players = new String[2];
    private Scanner scan = new Scanner(System.in);

    /*public Player_IDs(Turn turn, Dice dice)
    {
        getPlayerNames();
        startRoll(turn, dice);
    }*/

    public Player_IDs() { getPlayerNames(); }

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
    public void startRoll(Turn turn, Dice dice)
    {
        System.out.println("\n\nBoth players must now roll a die each - the player who gets the highest roll will go first. ");
        String input;

        do
        {
            System.out.println("Please enter 'X' to continue. ");
            input = scan.nextLine();
        }
        while(!input.matches("^([xX]{1})$"));

        ArrayList<Integer> rolls = dice.returnRolls();

        do
        {
            dice.clearDice();
            dice.rollDice(false);
        }
        while(rolls.getFirst() == rolls.getLast());

        System.out.println("\n"+ players[0] + " has rolled " +rolls.getFirst() + ".");
        System.out.println(players[1] + " has rolled " + rolls.getLast() + ".");

        if(rolls.getFirst() > rolls.getLast())
        {
            System.out.println("\n" + players[0] + " goes first. \n");
            turn.setTurn(false, false);
            return;
        }
        System.out.println("\n" + players[1] + " goes first. \n");
        turn.setTurn(true, true);
    }

}
