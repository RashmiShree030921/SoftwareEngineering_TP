import java.util.*;

public class Board
{
    //holds all the pip/ triangles on the board
    private Vector<Pip> Pips = new Vector<Pip>();
    //total numbers of pips on a board
    private int num_pips = 24;

    //object to hold the user's names and to decide player one
    //this object will handle taking user names
    private Player_IDs IDs;

    //bool to record the default orientation
    //e.g. the first person to enter their name takes white checkers
    // if white checkers go first, the default orientation has two white checkers to the far left
    // when turn doesnt match orientation, we need to flip the board around to display
    private boolean Orientation;
    private boolean Turn;

    //holds the dice roll values
    // doesn't have defined size so can take 4 values for when doubles are rolled
    // plan to clear this vector after each turn and repopulate after every new dice roll
    private Vector<Integer> rolls = new Vector<Integer>();
    private Random roll = new Random();

    //flag to note whenever we got a double roll
    // if double rolls found, possible moves function will need to handle up to four moves so need a flag for it
    private boolean doubleFlag = false;

    //vector to hold all the possible moves found
    //need this to allow decision functions to store commands for the movement functions
    //this will be cleared after every round
    List<int[]> Possible_Moves = new ArrayList<>();

    //need player IDs to generate the board because player one decides the orientation of the board
    public Board()
    {
        IDs = new Player_IDs();
        //populate the pip vector
        generateBoard(IDs.returnPlayerOne());
    }

    public void generateBoard(Boolean Orientation)
    {
        //pips need to be added in certain formation
        //start matrix holds the pattern for a half board
        // {0,5,7,11} are the pip indexes that need to have checkers
        //{2,5,3,5} are the number of checkers that need to be on these pips
        //e.g. pip 0 needs to have 2 checkers on it, pip 5 needs to have 5
        int[][] start_matrix = {{0,5,7,11}, {2,5,3,5}};
        //the colours the checkers need to be
        //if black goes first, use {false, true, true, false} first, if white first, use {true, false, false, true}
        boolean[][] start_colours = { {false, true, true, false}, {true, false, false, true} };

        //set default orientation
        this.Orientation = Orientation;
        this.Turn = Orientation;

        //offset selects orientation colours
        //if black is default orientation, select from start_colours[0] first
        //if white is default orientation, select from start_colours[1] first
        int offset = 0;
        if(Orientation)
            offset = 1;

        //add all the empty pips to the vector to begin
        for(int i =0; i< num_pips; i++)
            Pips.add(new Pip());

        //run this loop twice because we need to populate top and bottom of the board
        for(int j=0; j<2; j++)
            //go through indexes that need to be populated
            for(int i=0; i< start_matrix[0].length; i++)
                // run a loop iteration for each checker that needs to be added
                for(int k=0; k < start_matrix[1][i]; k++)
                {
                    // this equation allows us to populate the pattern of the top row of the board
                    // board is asymmetrical so can't use the same index pattern for the top
                    int index = (int) (Math.pow(-1,j)*(start_matrix[0][i])+( j*(num_pips-1)));
                    //if we're populating the top board, need to select the alternate colour pattern (i.e. use %2)
                    Pips.get(index).addChecker(new Checker(start_colours[(j+offset)%2][i]));
                }

    }

    //change turn
    public void changeTurn() { this.Turn = !(Turn); }


    //handles rolling the new dice values and printing the results
    public void rollDice()
    {
        //check that the dice vector was properly emptied
        if(rolls.isEmpty())
        {
            //roll the new dice values
            rolls.add(roll.nextInt(6) + 1);
            rolls.add(roll.nextInt(6) + 1);
            //set the doube flag if doubles found
            doubleFlag = (rolls.get(0) == rolls.get(1));

            //add values rolled to a string for output
            String output = rolls.get(0) + "-" + rolls.get(1);

            if (doubleFlag)
            {
                //if doubles found, need to double the rolls vector and output string
                rolls.add(rolls.getFirst());
                rolls.add(rolls.getFirst());
                output = output + "-" + output;

                System.out.println(IDs.returnName(Turn) + " has rolled doubles! ");
            }
            //else need to organise the rolls into biggest to smallest (helps the decider functions)
            else
                if(rolls.get(0) < rolls.get(1))
                    Collections.swap(rolls, 0,1);

            //print what was rolled and the colour of turn
            System.out.print(new Checker(Turn).returnString() + " to play " + output + " .");
            return;
        }
        System.out.println("Dice cannot be rolled again until the previous rolls are used. ");
    }

    //checks if we can start moving checkers off the board
    // i.e. check if there are any checkers of this colours outside home area
    public boolean homeCheck()
    {
        for(int i =0; i<num_pips; i++)
            if(Pips.get(i).returnColour() == Turn)
            {
                if (Turn == Orientation)
                    if ((i > 5 && i < 12) || (i > 11 && i < 18))
                        return false;
                else
                {
                    //need to convert this to alternate orientation
                    // home area indexes are different in the opposite orientation
                    int j = convert2AlterIndex(i);
                    if ((j > 5 && j < 12) || (j > 11 && j < 18))
                        return false;
                }
            }
        //couldn't find anything outside home area - we can move checkers out of the board
        return true;
    }

    //checks if move can be made - returns index of destination pip
    // will return an index of -2 if cannot be done
    public int canMove(int from_pip, int steps)
    {
        //cannot move from empty pip
        if(!Pips.get(from_pip).isEmpty())
        {
            Pip dest_pip;
            int dest_index;
            //check if board is re-orientated
            if(Orientation == Turn)
                //check clockwise move
                // '%' used to make sure indexes >23 avoided
                dest_index = ((from_pip + steps)%(num_pips)) ;
            else
                //check anti-clockwise move
                // '%' used to avoid indexes <0
                dest_index = (((from_pip - steps) % num_pips + num_pips) % (num_pips));

            //compare colours of checkers on source pip to destination pip
            //if pip is blocked, we can't move there
            Boolean source_colour = Pips.get(from_pip).returnColour();
            dest_pip = Pips.get(dest_index);

            //block check is inside pip object
            if(!dest_pip.blockCheck(source_colour))
                return dest_index;
        }
        //return -2 if move is not possible
        return -2;
    }

    //check if can move checker off the board
    //need to check if rules require exactly one step out of bounds to go off board
    public boolean canMoveOff(int from_pip, int steps)
    {
        if(homeCheck())
        {
            if (!Pips.get(from_pip).isEmpty()) {
                if (Orientation == Turn)
                    return ((from_pip + steps == 24));
                else
                    return ((from_pip - steps == -1));
            }
        }
        return false;
    }

    //converts all indexes to the alternative orientation's indexes
    //*** THIS IS ONLY FOR DISPLAY PURPOSES********
    public int convert2AlterIndex(int i) { return ((i+12)%24); }


    // ******* DO NOT CALL THIS YET - WILL CAUSE SEG FAULT !! *********
    //for checking if there are any possible moves available following another move
    public boolean findPossibleMoves(int steps, int moved_pip, int offset)
    {
        for(int i=0; i<num_pips; i++)
        {
            Pip p = Pips.get(i);
            if(!p.isEmpty())
                if(p.returnColour() == Turn)
                {
                    if (i == moved_pip)
                        if((canMove(i, steps+offset) > -2) || (canMoveOff(i,steps+offset)))
                            return true;
                    else
                        if ((canMove(i, steps) > -2) || (canMoveOff(i, steps)))
                            return true;
                }

        }
        return false;
    }
    private boolean flag = false;

    // ******* DO NOT CALL THIS YET - WILL CAUSE SEG FAULT !! *********
    //for finding moves involving both dice values
    public void showPossible()
    {

        System.out.println("Select from: ");
        int rolls_len = rolls.size();

        for(int i = 0; i< rolls_len ; i++)
        {
            for(int j = 0; j<num_pips; j++)
            {
                Pip p = Pips.get(j);
                int dest_index;

                if(!p.isEmpty())
                    if(p.returnColour() == Turn)
                    {
                        dest_index = canMove(j, i);
                        if (dest_index > -2)
                        {
                            if(findPossibleMoves(((i+1)%rolls_len), j, i) || flag)
                            {
                                if (Orientation == Turn)
                                    Possible_Moves.add(new int[]{j, dest_index});
                                else
                                    Possible_Moves.add(new int[]{convert2AlterIndex(j), convert2AlterIndex(dest_index)});
                            }
                        }
                        else if (canMoveOff(j, i))
                        {
                            if(findPossibleMoves(((i+1)%rolls_len), j, 0) || flag)
                            {
                                if (Orientation == Turn)
                                    Possible_Moves.add(new int[]{j, -1});
                                else
                                    Possible_Moves.add(new int[]{convert2AlterIndex(j), -1});
                            }
                        }
                    }
            }

            if(Possible_Moves.isEmpty() && !flag)
                break;
            else if (!Possible_Moves.isEmpty() && flag)
                return;
        }

        if(Possible_Moves.isEmpty() && !flag)
        {
            flag = true;
            showPossible();
            flag = false;
        }

    }

    // in progress
    public boolean CheckWin() { return false; }


    //displays the board in two orientations
    // regular orientation:
    // [ index 12 -> 23]
    // [ index 11 -> 0 ]

    //alternate orientation
    // [index 0 -> 11]
    // [index 23 -> 12]

    // all functions used the same vector regardless of orientation
    // orientation only changes the direction of movement
    // move up the index for regular orientation
    // move down the index when orientation is flipped
    // when displaying flipped board, pip numbers have to be converted
    public void Display()
    {
        String output = " ";

        if(Orientation == Turn)
        {
            for (int i = (num_pips / 2); i < num_pips; i++)
                output = output + Pips.get(i).Display() + ":" + i + " ";

            output = output +"\n\n ";

            for (int i = (num_pips / 2) - 1; i >= 0; i--)
                output = output + Pips.get(i).Display() + ":"+ i + " ";

            System.out.println(output);
        }
        else
        {
            for(int i = 0; i < (num_pips/2) ; i++)
                output = output + Pips.get(i).Display() + ":" + convert2AlterIndex(i) + " ";

            output = output + "\n\n ";

            for (int i = num_pips-1; i > (num_pips / 2)-1 ; i--)
                output = output + Pips.get(i).Display() + ":" + convert2AlterIndex(i) + " " ;

            System.out.println(output);
        }

    }
}
