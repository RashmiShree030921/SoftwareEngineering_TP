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
    private Board board;


    public Movement(Dice dice, ArrayList<Pip> pips, Turn turn, ArrayList<Pip> Bar, Board board) {
        this.dice = dice;
        this.turn = turn;
        this.Pips = pips;
        this.Bar = Bar;
        this.genCommands = new Command_Generator(dice, Pips, turn, Bar);
        this.board = board;
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
        return turn.returnTurn() == turn.returnOrientation() ? (removedCheckers[0] == 15) : (removedCheckers[1] == 15);
    }

    public void moveChecker() {
        Display();
        populateCommands();
        int total_steps = genCommands.returnTotalSteps();
        boolean turn_flag = (turn.returnTurn() != turn.returnOrientation());

        while (total_steps > 0) {
            nextMove = genCommands.selectCommand();
            if (nextMove != null) {
                int start = nextMove.returnStart();
                int end = nextMove.returnEnd();

                if (turn_flag && start != -1) start = convert2AlterIndex(start);
                if (turn_flag && end != -1) end = convert2AlterIndex(end);

                if (start == -1 || !Pips.get(start).isEmpty()) {
                    Checker checker;
                    if (start == -1) {
                        checker = turn_flag ? Bar.get(1).removeChecker() : Bar.get(0).removeChecker();
                    } else {
                        checker = Pips.get(start).removeChecker();
                    }

                    if (checker != null) {
                        if (end == -1) {
                            removedCheckers[turn.returnTurn() == turn.returnOrientation() ? 0 : 1]++;
                        } else {
                            if (!Pips.get(end).isEmpty()) {
                                Pip check = Pips.get(end);
                                if (check.returnColour() != turn.returnTurn()) {
                                    Checker oldCheck = check.removeChecker();
                                    if (turn_flag) {
                                        Bar.get(0).addChecker(oldCheck);
                                    } else {
                                        Bar.get(1).addChecker(oldCheck);
                                    }
                                }
                            }

                            Pips.get(end).addChecker(checker);

                            if (turn.returnTurn() == turn.returnOrientation()) {
                                total_steps -= start == -1 ? (((end + 1) % num_pips) + num_pips) % num_pips : (((end - start) % num_pips) + num_pips) % num_pips;
                            } else {
                                total_steps -= start == -1 ? ((((23 - end) + 1) % num_pips) + num_pips) % num_pips : (((start - end) % num_pips) + num_pips) % num_pips;
                            }

                            System.out.println("Moved checker from pip " + start + " to pip " + end);
                            System.out.println("Moves Left: " + total_steps);
                        }

                        if (total_steps > 0) {
                            Display();
                            updateCommands(nextMove);
                        }
                    } else {
                        System.out.println("No checker to move from pip " + start);
                    }
                }
            } else {
                System.out.println("Error occurred when moving checkers. Please select again.");
            }
        }
        dice.clearDice();
        turn.changeTurn();
    }


    public int convert2AlterIndex(int i) {
        return (((num_pips - 1 - i) + num_pips) % num_pips);
    }

    public void Display() {
        String output = " ";

        if (turn.returnOrientation() == turn.returnTurn()) {
            for (int i = (num_pips / 2); i < num_pips; i++)
                output = output + Pips.get(i).Display() + ":" + i + " ";

            output = output + "\n\n ";

            for (int i = (num_pips / 2) - 1; i >= 0; i--)
                output = output + Pips.get(i).Display() + ":" + i + " ";

            System.out.println(output);
        } else {
            for (int i = 0; i < (num_pips / 2); i++)
                output = output + Pips.get(i).Display() + ":" + convert2AlterIndex(i) + " ";

            output = output + "\n\n ";

            for (int i = num_pips - 1; i > (num_pips / 2) - 1; i--)
                output = output + Pips.get(i).Display() + ":" + convert2AlterIndex(i) + " ";

            System.out.println(output);
        }

        System.out.println("Bar: First: " + Bar.get(0).returnLength() + "    Second: " + Bar.get(1).returnLength());
    }



    public boolean processCommand(String command) {
        if (command.toLowerCase().equalsIgnoreCase("pip")) {
            pipCount();
            return true;
        } else if (command.toLowerCase().equalsIgnoreCase("hint")) {
            hint();
            return true;
        } else if (command.toLowerCase().equalsIgnoreCase("new match")) {
            new_match(command);
            return true;
        }else if (command.toLowerCase().startsWith("dice")) {
            // Use regular expression to match different dice formats
            dice_set(command);
            return true;
        }
        else {
            System.out.println("Error");
        }
        return false;
    }



    private void pipCount() {
        int whiteCount = 0, blackCount = 0;
        for (Pip pip : Pips) {
            if (!pip.isEmpty()) {
                if (pip.returnColour()) whiteCount += pip.returnLength();
                else blackCount += pip.returnLength();
            }
        }
        System.out.println("Pip count - White: " + whiteCount + ", Black: " + blackCount);
    }

    private void hint() {
        if (genCommands.returnTotalSteps() == 0 || nextMove == null) {
            System.out.println("No hint available yet. Please roll the dice and make a move first.");
        } else {
            System.out.println("HINT: Below are a list of moves you can make:  ");
            genCommands.displayCommands(); // Adjust this to only show valid commands excluding test commands
        }
    }

    public boolean dice_set(String command) {
        String regex = "(?i)dice\\s*(\\d)\\s*(\\d)|dice\\s*(\\d)(\\d)|dice(\\d)(\\d)";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(command);

        if (matcher.find()) {
            int roll1 = 0, roll2 = 0;

            if (matcher.group(1) != null && matcher.group(2) != null) {
                roll1 = Integer.parseInt(matcher.group(1));
                roll2 = Integer.parseInt(matcher.group(2));
            } else if (matcher.group(3) != null && matcher.group(4) != null) {
                roll1 = Integer.parseInt(matcher.group(3));
                roll2 = Integer.parseInt(matcher.group(4));
            } else if (matcher.group(5) != null && matcher.group(6) != null) {
                roll1 = Integer.parseInt(matcher.group(5));
                roll2 = Integer.parseInt(matcher.group(6));
            }

            dice.setDiceRolls(roll1, roll2);
            System.out.println("Dice set to: " + roll1 + " and " + roll2);
        }
        return true;
    }

    public boolean new_match(String command) {
        System.out.print("Do you want to start a new match? (yes/no): ");
        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine().trim();
        if (response.toLowerCase().equalsIgnoreCase("yes")) {
            System.out.println("Starting a new match...");
            board.matchScore[0] = 0;
            board.matchScore[1] = 0;
            board.resetBoard();
            board.play();
        }
        return true;
    }

    public int calculateScore() {
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
}



