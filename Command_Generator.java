import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Command_Generator
{
    private Scanner scan = new Scanner(System.in);

    private ArrayList<Integer> rolls;

    private ArrayList<ArrayList<Command>> Possible_Moves = new ArrayList<ArrayList<Command>>();
    HashMap<Integer, ArrayList<Integer> > offsets = new HashMap<Integer, ArrayList<Integer>>(5);

    private ArrayList<Command> nextMove = new ArrayList<Command>();

    private Dice dice;
    private Turn turn;

    private int move_count = 0;
    private int total_steps = 0;
    private int num_pips = 24;

    private ArrayList<Pip> Pips;
    private Move_Check canMove;

    public Command_Generator(Dice dice, ArrayList<Pip> pips, Turn turn)
    {
        this.dice = dice;
        this.turn = turn;
        this.rolls = dice.returnRolls();
        this.Pips = pips;
        this.canMove = new Move_Check(Pips, turn);
    }

    public int returnTotalSteps() { return total_steps; }

    public Command selectCommand()
    {
        String input = " ";
        int result = 0;
        boolean bounds = false;

        while(!input.matches("^(?!.*(.).*\\1)([a-zA-Z])$") || !bounds)
        {
            input = scan.nextLine();

            if(input.matches("^(?!.*(.).*\\1)([a-zA-Z])$") )
            {
                result = (input.toUpperCase()).charAt(0) - 'A';
                bounds = (result >= 0 && (result < nextMove.size() ));
                if(!bounds)
                    System.out.println("Please enter valid options only. ");
            }
            else
                System.out.println("Please enter a valid integer associated with a command option. ");

        }
        return nextMove.get(result);
    }

    public void displayCommands()
    {
        System.out.print(turn.returnColour() + " to play " + rolls.getFirst());
        for( int num = 1; num< rolls.size(); num++)
            System.out.print(  "-" + rolls.get(num) );
        System.out.print(".");

        System.out.println(" Select from: ");
        for(int i=0; i < nextMove.size(); i++)
            System.out.println((char)('A' + i) + ")" + " Play" + (nextMove.get(i)).commandString() + " ");
    }

    public void displayAllCommands()
    {
        for( ArrayList<Command> c: Possible_Moves)
        {
            for (Command b : c)
                System.out.print(b.commandString());
            System.out.println(" ");
        }
        System.out.println(" ");
    }

    private void updateNext()
    {
        nextMove.clear();

        if(!Possible_Moves.isEmpty())
        {
            for (ArrayList<Command> c : Possible_Moves)
                if (!checkNextDuplicate(c.getFirst()))
                    nextMove.add(c.getFirst());

        }
    }

    public void updateCommands(Command next)
    {
        if(!Possible_Moves.isEmpty())
        {
            for (int i= Possible_Moves.size()-1 ; i>=0; i--)
            {
               ArrayList<Command> c = Possible_Moves.get(i);

               if (!next.isEqual((c.getFirst())))
                   Possible_Moves.remove(c);
               else
                   c.removeFirst();
            }
        }
        updateNext();
    }

    private boolean checkNextDuplicate(Command c)
    {
        for(Command b: nextMove )
            if(c.isEqual(b))
                return true;
        return false;
    }


    public void processCommands()
    {
        if(rolls.isEmpty())
            return;

        Possible_Moves.clear();

        int roll_iterations = rolls.size();

        if(dice.returnDoubleFlag())
        {
            move_count = 4*(rolls.getFirst());
            roll_iterations = 1;
            while(Possible_Moves.isEmpty() && move_count > 0)
            {
                for(int i=0; i<roll_iterations; i++)
                    generateMoves(i, 0, rolls.size()-i);

                if((move_count - rolls.getFirst())<=0 || !Possible_Moves.isEmpty())
                    total_steps = move_count;
                move_count -= rolls.getFirst();
            }
        }
        else
        {
            move_count = rolls.getFirst() + rolls.getLast();

            while(Possible_Moves.isEmpty() && roll_iterations > 0)
            {
                for(int j=0; j< (rolls.size()-roll_iterations)+1; j++)
                    if(Possible_Moves.isEmpty())
                        for (int i = 0; i < roll_iterations; i++)
                        {
                            if((rolls.size()-roll_iterations) > 0)
                                move_count = rolls.get(i);
                            generateMoves((i + j) % rolls.size(), 0, rolls.size()-j);
                        }
                roll_iterations--;
            }
            total_steps = move_count;
        }
        updateNext();
    }

    private ArrayList<ArrayList<Command>> generateMoves(int roll_index, int sum, int rolls_len)
    {
        int i = rolls.get(roll_index);
        int index;

        int dest_index;
        boolean add_vec ;
        boolean add_offset ;

        boolean turn_flag = (turn.returnTurn() != turn.returnOrientation());
        int offset;

        ArrayList<ArrayList<Command>> returning = new ArrayList<ArrayList<Command>>();

        for (int j = 0; j < num_pips; j++)
        {
            Pip p = Pips.get(j);

            if (!p.isEmpty())
                if (p.returnColour() == turn.returnTurn())
                {
                    add_vec = !offsets.containsKey(j);
                    if(add_vec)
                        offsets.put(j, new ArrayList<Integer>());

                    add_offset = offsets.get(j).size() < Pips.get(j).returnLength();
                    if(add_offset)
                        offsets.get(j).add(0);

                    for(int offset_index = 0; offset_index < offsets.get(j).size(); offset_index ++)
                    {
                        offset = offsets.get(j).get(offset_index);
                        index = ((i+offset)%num_pips + num_pips)%num_pips;

                        dest_index = canMove.canMove(j, index);

                        if (dest_index > -2)
                        {
                            returning.add(new ArrayList<Command>());

                            if(!turn_flag)
                            {
                                index = (((j + offset) % num_pips) + num_pips) % num_pips;
                                returning.getLast().add(new Command(index, (dest_index)));
                            }
                            else
                            {
                                index = (((j - offset) % num_pips) + num_pips) % num_pips;
                                returning.getLast().add(new Command(canMove.convert2AlterIndex(index), canMove.convert2AlterIndex(dest_index)));
                            }

                            if (sum + rolls.get(roll_index) != move_count)
                            {
                                offsets.get(j).set(offset_index, offset + i);

                                ArrayList<ArrayList<Command>> select = generateMoves(((roll_index + 1) % rolls_len), sum + i, rolls_len);

                                offsets.get(j).set(offset_index, offsets.get(j).get(offset_index) - i);

                                if (!select.isEmpty())
                                {
                                    for (ArrayList<Command> a : select)
                                    {
                                        a.addFirst(returning.getLast().getLast());
                                        if (a.size() == rolls_len)
                                        {
                                            if(consolidateCommands(a))
                                            {
                                                if (!checkDuplicate(a))
                                                    Possible_Moves.add(a);
                                            }
                                            else
                                                Possible_Moves.add(a);
                                        }
                                    }

                                    if (select.getFirst().size() != rolls_len)
                                        returning.addAll(select);
                                }
                            }
                        }
                    }

                    if(add_offset)
                        offsets.get(j).removeLast();
                    if(add_vec)
                        offsets.remove(j);
                }

            if(sum==0)
                returning = new ArrayList<ArrayList<Command>>();
        }

        return returning;
    }

    private boolean consolidateCommands(ArrayList<Command> a)
    {
        boolean joinFlag = false;

        for(int m = a.size()-1 ; m > 0; m--)
        {
            if (a.get(m).returnStart() == a.get(m - 1).returnEnd())
            {
                Command new_com = joinCommands(a, m);
                a.add(m-1, new_com);
                joinFlag = true;
            }
        }

        return joinFlag;
    }

    private Command joinCommands(ArrayList<Command> a, int index)
    {
        int start = a.get(index-1).returnStart();
        int end = a.get(index).returnEnd();

        Command new_comm = new Command(start, end);
        a.remove(index);
        a.remove(index-1);
        return new_comm;
    }

    private boolean checkDuplicate(ArrayList<Command> c)
    {
        for (ArrayList<Command> a:  Possible_Moves)
        {
            if (c.size() == a.size())
            {
                boolean flag = true;

                for (int i = 0; i < c.size(); i++)
                {
                    Command com = c.get(i);
                    //System.out.println("Comparing: " + com.commandString() + "  " + b.get(i).commandString());
                    if (!(com.isEqual(a.get(i))))
                    {
                        // System.out.println("Do not match: " + com.commandString() + "  " + b.get(i).commandString());
                        flag = false;
                        break;
                    }
                }

                if (flag)
                    return true;
            }
        }

        return false;
    }
}



