import java.util.ArrayList;

public class Movement
{
    private int num_pips = 24;
    private Command_Generator genCommands;

    private ArrayList<Pip> Pips;
    private ArrayList<Pip> Bar;

    private int[] removedCheckers = {0,0};

    private Dice dice;
    private Turn turn;

    private Command nextMove;

    public Movement(Dice dice, ArrayList<Pip> pips, Turn turn, ArrayList<Pip> Bar)
    {
        this.dice = dice;
        this.turn = turn;
        this.Pips = pips;
        this.Bar = Bar;
        this.genCommands = new Command_Generator(dice, Pips, turn, Bar);
    }

    private void populateCommands()
    {
        dice.rollDice(true);
        genCommands.processCommands();
        genCommands.displayCommands();
    }

    private void updateCommands(Command move)
    {
        genCommands.updateCommands(move);
        genCommands.displayCommands();
    }

    public boolean checkWin()
    {
        if(turn.returnTurn() == turn.returnOrientation())
            return (removedCheckers[0] ==15) ;
        else
            return ( removedCheckers[1] == 15);
    }

    public void moveChecker()
    {
        Display();
        populateCommands();
        int total_steps = genCommands.returnTotalSteps();
        //System.out.println("total is now: " + total_steps);

        boolean turn_flag = (turn.returnTurn() != turn.returnOrientation());

        while(total_steps > 0)
        {
            nextMove = genCommands.selectCommand();
            //System.out.println("command" + nextMove.commandString());

            if (nextMove != null)
            {
                int start = nextMove.returnStart();
                int end = nextMove.returnEnd();

                if(turn_flag && start != -1 )
                    start = convert2AlterIndex(start);
                if(turn_flag && end != -1 )
                    end = convert2AlterIndex(end);


                if(start ==-1 || !Pips.get(start).isEmpty() )
                {
                    Checker checker;
                    if(start == -1)
                    {
                        if(!turn_flag)
                            checker = Bar.getFirst().removeChecker();
                        else
                            checker = Bar.getLast().removeChecker();
                    }
                    else
                        checker = Pips.get(start).removeChecker();

                    if (checker != null)
                    {
                        if(end == -1)
                        {
                            if(turn.returnTurn() == turn.returnOrientation())
                                removedCheckers[0] ++;
                            else
                                removedCheckers[1]++;
                        }
                        else
                        {
                            if(!Pips.get(end).isEmpty())
                            {
                                Pip check = Pips.get(end);
                                if(check.returnColour() != turn.returnTurn())
                                {
                                    Checker oldCheck = check.removeChecker();
                                    if (!turn_flag)
                                        Bar.getLast().addChecker(oldCheck);
                                    else
                                        Bar.getFirst().addChecker(oldCheck);
                                }
                            }

                            Pips.get(end).addChecker(checker);

                            if (turn.returnTurn() == turn.returnOrientation())
                            {
                                if(start ==-1)
                                    total_steps -= (((end+1) % num_pips) + num_pips) % num_pips;
                                else
                                    total_steps -= (((end - start) % num_pips) + num_pips) % num_pips;

                            }
                            else
                            {
                                if(start == -1)
                                    total_steps -= ((((23-end)+1)% num_pips) + num_pips) % num_pips;
                                else
                                    total_steps -= (((start - end) % num_pips) + num_pips) % num_pips;

                            }
                           System.out.println("Moved checker from pip " + start + " to pip " + (end));
                           System.out.println("Moves Left: " + total_steps);
                        }

                        if(total_steps > 0)
                        {
                            Display();
                            updateCommands(nextMove);
                        }
                    }
                    else
                        System.out.println("No checker to move from pip " + start);
                }
            }
            else
                System.out.println("Error occurred when moving checkers. Please select again.");
        }
        dice.clearDice();
        turn.changeTurn();
    }

    public int convert2AlterIndex(int i) { return (((num_pips-1-i)+num_pips)%num_pips); }

    public void Display()
    {
        String output = " ";

        if(turn.returnOrientation() == turn.returnTurn())
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

        System.out.println("Bar: First: " + Bar.getFirst().returnLength() + "    Second: " + Bar.getLast().returnLength());

    }

}
