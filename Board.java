import java.util.*;

public class Board
{
    private ArrayList<Pip> Pips = new ArrayList<Pip>();

    private final int num_pips = 24;

    private final Player_IDs IDs;

    private Turn turn = new Turn();
    private ArrayList<Integer> rolls = new ArrayList<Integer>();
    private Dice dice = new Dice(rolls);

    private Movement movement;

    private Command_Generator genCommands;


    public Board()
    {
        this.IDs = new Player_IDs(turn, dice);
        generateBoard();
        movement = new Movement(dice, Pips, turn);
    }

    public void generateBoard()
    {

        int[][] start_matrix = {{0,5,7,11}, {2,5,3,5}};
        boolean[][] start_colours = { {false, true, true, false}, {true, false, false, true} };

        int offset = 0;
        if(turn.returnOrientation())
            offset = 1;

        for(int i =0; i< num_pips; i++)
            Pips.add(new Pip());

        for(int j=0; j<2; j++)
            for(int i=0; i< start_matrix[0].length; i++)
                for(int k=0; k < start_matrix[1][i]; k++)
                {
                    int index = (int) (Math.pow(-1,j)*(start_matrix[0][i])+( j*(num_pips-1)));
                    Pips.get(index).addChecker(new Checker(start_colours[(j+offset)%2][i]));
                }

    }

    public void play()
    {
        while(!movement.checkWin())
            movement.moveChecker();
    }

}
