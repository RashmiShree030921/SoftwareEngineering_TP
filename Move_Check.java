import java.util.*;

public class Move_Check
{
    private final int num_pips = 24;
    private ArrayList<Pip> Pips;
    private ArrayList<Pip> Bar;

    private Turn turn;
    private boolean barFlag = false;

    public Move_Check(ArrayList<Pip> Pips, Turn turn, ArrayList<Pip> Bar)
    {
        this.Pips = Pips;
        this.turn = turn;
        this.Bar = Bar;
    }

    public void updateBarFlag()
    {
        if(turn.returnTurn() == turn.returnOrientation()) {
            if (!Bar.getFirst().isEmpty())
                barFlag = true;
            else
                barFlag = false;
        }
        else {
            if (!Bar.getLast().isEmpty())
                barFlag = true;
            else
                barFlag = false;
        }

    }

    public boolean returnBarFlag() { return barFlag; }
    public void setBarFlag(boolean set) { barFlag = set; }

   public int convert2AlterIndex(int i) { return (((num_pips-1-i)+num_pips)%num_pips); }
    //checks if we can start moving checkers off the board
    // i.e. check if there are any checkers of this colours outside home area
    public boolean homeCheck()
    {
        boolean turnFlag = turn.returnTurn() == turn.returnOrientation();

        if(turnFlag && !Bar.getFirst().isEmpty())
            return false;
        else if(!Bar.getLast().isEmpty())
            return false;

        for(int i =0; i<num_pips; i++)
        {
            if(!Pips.get(i).isEmpty())
                if (Pips.get(i).returnColour() == turn.returnTurn())
                {
                    if (turnFlag)
                    {
                        if (i < 18)
                            return false;
                    }
                    else {
                        if (i > 5 && Bar.getLast().isEmpty())
                            return false;
                    }

                }
        }
        //couldn't find anything outside home area - we can move checkers out of the board
        return true;
    }

    //checks if move can be made - returns index of destination pip
    // will return an index of -2 if cannot be done
    public int canMove(int from_pip, int steps)
    {
        if(steps<=0)
            return -2;
        boolean turnFlag = (turn.returnTurn() == turn.returnOrientation());

        if(barFlag)
        {
            if(barCount() != 0)
            {
                barFlag = false;
                int result;

                result = canMove(from_pip, steps);

                barFlag = true;
                return result;
            }
            return -2;
        }
        else
        {
            if (canMoveOff(from_pip, steps))
                return -1;

            if (from_pip ==-1 ||!Pips.get(from_pip).isEmpty() )
            {
                Pip dest_pip;
                int dest_index;
                Boolean source_colour;

                if((from_pip == -1))
                {
                    if(barCount() == 0)
                        return -2;

                    if (turnFlag)
                    {
                        dest_index = ((steps-1) % (num_pips));
                        source_colour = Bar.getFirst().returnColour();
                    }
                    else
                    {
                        dest_index = (((23 - (steps-1)) % num_pips + num_pips) % (num_pips));
                        source_colour = Bar.getLast().returnColour();
                    }

                    dest_pip = Pips.get(dest_index);
                }
                else
                {
                    if (turnFlag)
                        dest_index = ((from_pip + steps) % (num_pips));
                    else
                        dest_index = (((from_pip - steps) % num_pips + num_pips) % (num_pips));

                    source_colour = Pips.get(from_pip).returnColour();
                    dest_pip = Pips.get(dest_index);
                }

                if (!dest_pip.blockCheck(source_colour))
                    return dest_index;
            }
        }
        //return -2 if move is not possible
        return -2;
    }

    //check if can move checker off the board
    //need to check if rules require exactly one step out of bounds to go off board
    public boolean canMoveOff(int from_pip, int steps)
    {
        boolean turnFlag = turn.returnOrientation() == turn.returnTurn();
        if(homeCheck())
        {
            if (from_pip == -1 || !Pips.get(from_pip).isEmpty())
            {
                if(from_pip==-1)
                {
                    if(turnFlag)
                        return((steps-1 == num_pips));
                    else
                        return(23-(steps-1) == -1);
                }
                else
                {
                    if (turnFlag)
                        return ((from_pip + steps == num_pips));
                    else
                        return ((from_pip - steps == -1));
                }
            }
        }
        return false;
    }

    public int barCount()
    {
        if(turn.returnTurn() == turn.returnOrientation())
        {
            if (!Bar.getFirst().isEmpty())
                return Bar.getFirst().returnLength();

        }
        else
        {
            if (!Bar.getLast().isEmpty())
                return Bar.getLast().returnLength();
        }

        return 0;
    }

}
