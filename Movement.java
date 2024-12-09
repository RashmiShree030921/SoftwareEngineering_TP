import java.util.ArrayList;
import java.util.Scanner;

public class Movement {
    private int num_pips = 24;
    private Command_Generator genCommands;

    private ArrayList<Pip> Pips;
    private ArrayList<Pip> Bar;

    private int[] removedCheckers = {0, 0};

    private Dice dice;
    private Turn turn;

    private Command nextMove;
    private Scanner scan = new Scanner(System.in);

    private boolean testMode;
    private TestFile testFile;

    public boolean restartFlag = false;
    public boolean doubleOwner;
    public int doubleCube=1;

    public Player_IDs IDs;
    PrintBoard printBoard;

    public Movement(Dice dice, ArrayList<Pip> pips, Turn turn, ArrayList<Pip> Bar, boolean testMode, TestFile testFile, Player_IDs IDs)
    {
        this.dice = dice;
        this.turn = turn;
        this.Pips = pips;
        this.Bar = Bar;
        this.genCommands = new Command_Generator(dice, Pips, turn, Bar, testFile, testMode);
        this.testMode = testMode;
        this.testFile = testFile;
        this.IDs = IDs;
        this.printBoard = new PrintBoard(IDs);
    }

    private void populateCommands() {
        if(doubleCube>1)
            System.out.println(IDs.returnName(doubleOwner) + " is currently the owner of the double cube.");
        else
            System.out.println("The double cube is currently unowned.");
        dice.rollDice(true);
        genCommands.processCommands();
        genCommands.displayCommands(IDs.returnName(turn.returnTurn()));
    }

    private void updateCommands(Command move) {
        genCommands.updateCommands(move);
        if(genCommands.returnTotalSteps()>0)
            genCommands.displayCommands(IDs.returnName(turn.returnTurn()));
    }

    public boolean checkWin() {
        if (turn.returnTurn() == turn.returnOrientation())
            return (removedCheckers[0] == 15);
        else
            return (removedCheckers[1] == 15);
    }


    public boolean moveChecker()
    {
        Display();
        populateCommands();
        int total_steps = genCommands.returnTotalSteps();


        boolean turn_flag = (turn.returnTurn() != turn.returnOrientation());

        while (total_steps > 0 )
        {
            if(restartFlag)
                return true;
            nextMove = processCommand();

            if (nextMove != null)
            {
                int start = nextMove.returnStart();
                int end = nextMove.returnEnd();

                if (turn_flag && start != -1)
                    start = convert2AlterIndex(start);
                if (turn_flag && end != -1)
                    end = convert2AlterIndex(end);


                if (start == -1 || !Pips.get(start).isEmpty())
                {
                    Checker checker;
                    if (start == -1)
                    {
                        if (!turn_flag)
                            checker = Bar.getFirst().removeChecker();
                        else
                            checker = Bar.getLast().removeChecker();
                    } else
                        checker = Pips.get(start).removeChecker();

                    if (checker != null)
                    {
                        if (end == -1) {
                            if (turn.returnTurn() == turn.returnOrientation())
                                removedCheckers[0]++;
                            else
                                removedCheckers[1]++;
                        } else {
                            if (!Pips.get(end).isEmpty()) {
                                Pip check = Pips.get(end);
                                if (check.returnColour() != turn.returnTurn()) {
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
                                if (start == -1)
                                    total_steps -= (((end + 1) % num_pips) + num_pips) % num_pips;
                                else if(start==end)
                                    total_steps -= num_pips;
                                else
                                    total_steps -= (((end - start) % num_pips) + num_pips) % num_pips;

                            }
                            else
                            {
                                if (start == -1)
                                    total_steps -= ((((23 - end) + 1) % num_pips) + num_pips) % num_pips;
                                else if(start ==end)
                                    total_steps -= num_pips;
                                else
                                    total_steps -= (((start - end) % num_pips) + num_pips) % num_pips;

                            }
                            System.out.println("Moved checker from pip " + start + " to pip " + (end));
                            System.out.println("Moves Left: " + total_steps);
                        }
                        if(total_steps >0)
                            Display();
                        updateCommands(nextMove);
                        if(genCommands.returnTotalSteps() <=0)
                            break;


                    } else
                        System.out.println("No checker to move from pip " + start);
                }
            }

        }
        dice.clearDice();
        turn.changeTurn();

        return true;
    }

    public int convert2AlterIndex(int i)
    {
        return (((num_pips - 1 - i) + num_pips) % num_pips);
    }

    public Command processCommand()
    {
        String command;
        if(testMode)
            command = (testFile.readNext());
        else
            command = (scan.nextLine().trim()).toLowerCase();
        Command returning = genCommands.selectCommand(command);
        //System.out.println("received: " + command);

        if (returning !=null)
            return returning;
        else if (command.equalsIgnoreCase("pip") )
           pipCount();
        else if (command.equalsIgnoreCase("hint"))
            hint();
        else if (command.startsWith("dice") )
            dice_set(command);
        else if(command.equalsIgnoreCase("new match"))
            new_match();
        else if(command.equalsIgnoreCase("doubles"))
            doubles();
        else
        {
            System.out.println("******** Invalid Command ********");
            System.out.println("\nPlease select one of the following commands below, or enter one of the following commands:");
            System.out.println("Pip - to see your pip count.\nHint - to view command hints." +
                    "\nDice - to set the value of the dice. \nNew Match - to start a new match. \nDoubles - to change ownership of the doubles cube.");
            genCommands.displayCommands(IDs.returnName(turn.returnTurn()));
        }
        return null;
    }

    public void pipCount()
    {
        int whiteCount = 0, blackCount = 0;
        for (Pip pip : Pips)
        {
            if (!pip.isEmpty())
            {
                if (pip.returnColour()) whiteCount += pip.returnLength();
                else blackCount += pip.returnLength();
            }
        }
        System.out.println("Pip count - White: " + whiteCount + ", Black: " + blackCount);
    }

    public void hint()
    {
        System.out.println("HINT: Below are a list of moves you can make:  ");
        genCommands.displayCommands(IDs.returnName(turn.returnTurn()));
    }

    public void dice_set(String command)
    {
        String regex = "(?i)dice\\s*(\\d)\\s*(\\d)|dice\\s*(\\d)(\\d)|dice(\\d)(\\d)";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(command);

        if (matcher.find())
        {
            int roll1 = Integer.parseInt(matcher.group(1));
            int roll2 = Integer.parseInt(matcher.group(2));

            dice.setDice(roll1, roll2);
            System.out.println("Dice set to: " + roll1 + " and " + roll2);
            return;
        }

        System.out.println("Invalid command. Please use the format 'dice <number> <number>'.");
    }


    public void resetBoard()
    {
        Pips.clear();
        Bar.clear();
        Bar.add(new Pip());
        Bar.add(new Pip());
        genCommands.clearCommands();
        restartFlag = false;
    }

    public boolean returnRestart(){ return restartFlag; }
    public void setRestart(boolean set) { restartFlag = set;  }

    public void new_match()
    {
        boolean flag = false;
        String response = " ";

        do
        {
            System.out.print("Do you want to start a new match? (yes/no): ");
            if(testMode)
                response = (testFile.readNext());
            else
                response = scan.nextLine().trim();

            if (response.toLowerCase().equalsIgnoreCase("yes"))
            {
                System.out.println("Starting a new match...");
                restartFlag = true;
                flag = true;
            }
            else if(response.toLowerCase().equalsIgnoreCase("no")) {
                flag = true;
            }

        }while(!flag);
    }

    private void doubles()
    {
        if(doubleCube ==1 || doubleOwner == turn.returnTurn())
        {
            if(doubleCube == 64)
            {
                System.out.println("You have reached the maximum factor of the doubles cube.");
                return;
            }
            boolean flag = false;
            String response = " ";

            do
            {
                System.out.println("Does your opponent, " + IDs.returnName(!turn.returnTurn()) + ", want to accept the double cube? (yes/no) ");
                if(testMode)
                    response = testFile.readNext();
                else
                    response = scan.nextLine();

                if (response.toLowerCase().equalsIgnoreCase("yes"))
                {
                    System.out.println("The double cube has changed ownership to " + IDs.returnName(!turn.returnTurn()) + " .");
                    doubleCube *=2;
                    doubleOwner = !turn.returnTurn();
                    flag = true;
                }
                else if(response.toLowerCase().equalsIgnoreCase("no"))
                {
                    if(doubleCube ==1)
                        System.out.println("The double cube remains unowned.");
                    else
                        System.out.println("The double cube remains in the possession of " + IDs.returnName(turn.returnTurn())+ " .");
                    flag = true;
                }
            }while (!flag);
        }
        else
            System.out.println("You do not have permission to offer doubles at this time.");


    }

    public int calculateScore()
    {
        int cubeValue = doubleCube;
        turn.changeTurn();

        doubleCube = 1;

        boolean orientationMatch = (turn.returnTurn() == turn.returnOrientation());
        int turn_index = (orientationMatch)?0:1;

        int full_pip = 15;
        if(removedCheckers[turn_index] != full_pip)
            return 0; //no win

        int opp_index = (orientationMatch)?1:0;
        boolean opponentBarCheck = (!Bar.get(opp_index).isEmpty());

        boolean opponentHomeCheck = false;
        int startIndex = orientationMatch ? 0 : 18;
        int endIndex = orientationMatch ? 6 : 24;
        for (int i = startIndex; i < endIndex; i++)
        {
            if (!Pips.get(i).isEmpty() && Pips.get(i).returnColour() != turn.returnTurn())
            {
                opponentHomeCheck = true;
                break;
            }
        }

        int opponentCheckerCount = full_pip - removedCheckers[opp_index];

        if (opponentCheckerCount == full_pip)
        {
            if (opponentBarCheck || opponentHomeCheck)
                return 3*cubeValue; // Backgammon

            return 2*cubeValue; // Gammon
        }

        return cubeValue; //Single
    }

    public void Display()
    {
        printBoard.printBoardLayout(Pips, turn, Bar);
    }
}
