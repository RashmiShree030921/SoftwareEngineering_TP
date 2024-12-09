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


    public Movement(Dice dice, ArrayList<Pip> pips, Turn turn, ArrayList<Pip> Bar, boolean testMode, TestFile testFile) {
        this.dice = dice;
        this.turn = turn;
        this.Pips = pips;
        this.Bar = Bar;
        this.genCommands = new Command_Generator(dice, Pips, turn, Bar, testFile, testMode);
        this.testMode = testMode;
        this.testFile = testFile;
    }

    private void populateCommands() {
        dice.rollDice(true);
        genCommands.processCommands();
        genCommands.displayCommands();
    }

    private void updateCommands(Command move) {
        genCommands.updateCommands(move);
        genCommands.displayCommands();
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

        while (total_steps > 0 && !restartFlag)
        {
            nextMove = processCommand();

            if (nextMove != null)
            {
                int start = nextMove.returnStart();
                int end = nextMove.returnEnd();

                if (turn_flag && start != -1)
                    start = convert2AlterIndex(start);
                if (turn_flag && end != -1)
                    end = convert2AlterIndex(end);


                if (start == -1 || !Pips.get(start).isEmpty()) {
                    Checker checker;
                    if (start == -1) {
                        if (!turn_flag)
                            checker = Bar.getFirst().removeChecker();
                        else
                            checker = Bar.getLast().removeChecker();
                    } else
                        checker = Pips.get(start).removeChecker();

                    if (checker != null) {
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

                        if (total_steps > 0) {
                            Display();
                            updateCommands(nextMove);
                        }
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
            command = testFile.readNext();
        else
            command = (scan.nextLine().trim()).toLowerCase();
        Command returning = genCommands.selectCommand(command);
        System.out.println("received: " + command);

        if (returning !=null)
            return returning;

        else if (command.equalsIgnoreCase("pip") )
        {
           pipCount();
            return null;
        }
        else if (command.equalsIgnoreCase("hint"))
        {
            hint();
            return null;
        }
        else if (command.startsWith("dice") )
        {
            dice_set(command);
            return null;
        }
        else if(command.startsWith("new match"))
        {
            new_match();
            return null;
        }

        System.out.println("Invalid Command - Please try again.");
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
        genCommands.displayCommands();
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
        restartFlag = true;
    }

    public boolean returnRestart(){ return restartFlag; }
    public void setRestart(boolean set) { restartFlag = set;  }

    public void new_match()
    {
        boolean flag = false;

        while(!flag) {
            System.out.print("Do you want to start a new match? (yes/no): ");


            String response = scan.nextLine().trim();

            if (response.toLowerCase().equalsIgnoreCase("yes")) {
                System.out.println("Starting a new match...");
                resetBoard();
                flag = true;
            }
        }

    }

    public int calculateScore()
    {
        boolean opponentHasCheckersOnBar = false;
        for (Pip barPip : Bar) {
            if (!barPip.isEmpty()) {
                opponentHasCheckersOnBar = true;
                break;
            }
        }

        boolean opponentHasCheckersInHomeBoard = false;
        int startIndex = turn.returnOrientation() ? 0 : 18;
        int endIndex = turn.returnOrientation() ? 6 : 24;
        for (int i = startIndex; i < endIndex; i++) {
            if (!Pips.get(i).isEmpty() && Pips.get(i).returnColour() != turn.returnTurn()) {
                opponentHasCheckersInHomeBoard = true;
                break;
            }
        }

        int opponentCheckerCount = 0;
        for (Pip pip : Pips) {
            if (!pip.isEmpty() && pip.returnColour() != turn.returnTurn()) {
                opponentCheckerCount += pip.returnLength();
            }
        }

        if (removedCheckers[turn.returnTurn() ? 1 : 0] == 15) {
            if (opponentHasCheckersOnBar || opponentHasCheckersInHomeBoard) {
                return 3; // Backgammon
            } else if (opponentCheckerCount == 0) {
                return 2; // Gammon
            }
            return 1; // Single
        }

        return 0; // No points if the game is not finished
    }

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
        System.out.println("Off: First: " + removedCheckers[0] + "   Second:  " + removedCheckers[1]);
    }

}
