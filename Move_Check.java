import java.util.*;

public class Move_Check
{
    private final int num_pips = 24;
    private ArrayList<Pip> Pips;

    private Turn turn;

    public Move_Check(ArrayList<Pip> Pips, Turn turn)
    {
        this.Pips = Pips;
        this.turn = turn;
    }

    public int convert2AlterIndex(int i) { return (((num_pips-1-i)+num_pips)%num_pips); }
    //checks if we can start moving checkers off the board
    // i.e. check if there are any checkers of this colours outside home area
    public boolean homeCheck()
    {
        for(int i =0; i<num_pips; i++)
        {
            if(!Pips.get(i).isEmpty())
                if (Pips.get(i).returnColour() == turn.returnTurn())
                {
                    if (turn.returnTurn() == turn.returnOrientation())
                        if ((i > 5 && i < 12) || (i > 11 && i < 18))
                            return false;
                        else {
                            //need to convert this to alternate orientation
                            // home area indexes are different in the opposite orientation
                            int j = convert2AlterIndex(i);
                            if ((j > 5 && j < 12) || (j > 11 && j < 18))
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
        if(canMoveOff(from_pip, steps))
            return -1;
        //cannot move from empty pip
        if(!Pips.get(from_pip).isEmpty())
        {
            Pip dest_pip;
            int dest_index;
            //check if board is re-orientated
            if(turn.returnOrientation() == turn.returnTurn())
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
            if (!Pips.get(from_pip).isEmpty())
            {
                if (turn.returnOrientation() == turn.returnTurn())
                    return ((from_pip + steps == num_pips));
                else
                    return ((from_pip - steps == -1));
            }
        }
        return false;
    }

}
