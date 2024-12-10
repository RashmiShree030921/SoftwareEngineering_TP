import java.util.*;

public class Board
{
    private ArrayList<Pip> Pips = new ArrayList<Pip>();
    private ArrayList<Pip> Bar = new ArrayList<Pip>(2);
    Scanner scanner = new Scanner(System.in);

    private final int num_pips = 24;

    private final Player_IDs IDs;

    private Turn turn = new Turn();
    private ArrayList<Integer> rolls = new ArrayList<Integer>();
    private Dice dice = new Dice(rolls);

    private Movement movement;
    private TestFile testFile = new TestFile();

    public int matchLength;
    public int[] matchScore = {0, 0};

    private boolean testMode = false;

    public Board()
    {
        askTest();
        this.IDs = new Player_IDs(turn, dice, testMode,testFile);
        this.Bar.add(new Pip());
        this.Bar.add(new Pip());
        generateBoard();
        movement = new Movement(dice, Pips, turn, Bar, testMode,testFile, IDs);
        setMatchLength();
    }
    public void askTest()
    {
        boolean flag = false;

        while(!flag)
        {
            System.out.print("Do you want to the game in test mode? (yes/no)");
            String response = scanner.nextLine().trim();

            if (response.toLowerCase().equalsIgnoreCase("yes")) {
                testMode = true;
                flag = true;
            }
            else if(response.toLowerCase().equalsIgnoreCase("no"))
                flag = true;
        }

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

    public void initializeGame()
    {
        movement.resetBoard();
        IDs.startRoll(turn,dice);
        generateBoard();
        setMatchLength();
    }

    public void play()
    {
        System.out.println("The game has started.");
        while(!movement.checkWin() && !movement.returnRestart())
        {
            movement.setRestart(false);
            displayMatchInfo();
            boolean proceed = false;

            while (!proceed)
            {
                System.out.println("\nPlease select one of the following commands below, or enter one of the following commands:");
                System.out.println("Pip - to see your pip count.\nHint - to view command hints." +
                            "\nDice - to set the value of the dice. \nNew Match - to start a new match. \nDoubles - to change ownership of the doubles cube.");

                proceed = movement.moveChecker();
            }

        }

        if(!movement.returnRestart())
        {
            int gameScore = movement.calculateScore();
            updateMatchScore(gameScore);
            announceGameResult(gameScore);
        }


        if (matchScore[0] < matchLength && matchScore[1] < matchLength)
        {
            if(!movement.returnRestart())
                System.out.println("Starting next game...");
            initializeGame();
            play();
        }
        else
        {
            System.out.println("Match over! " + (matchScore[0] >= matchLength ? IDs.returnName(turn.returnOrientation()) : IDs.returnName(!turn.returnOrientation())) + " wins the match!");
        }

    }

    public void setMatchLength()
    {
        boolean flag = false;
        do
        {
            System.out.println("Please enter a valid match length. ");

            try {
                if(testMode)
                {
                    matchLength = testFile.nextInt();
                    flag = true;
                }
                else
                {
                    if (scanner.hasNextInt())
                    {
                        matchLength = scanner.nextInt();
                        flag = true;
                    }
                    else
                        scanner.nextLine();
                }
            }
            catch(Exception ex)
            {
                System.out.println("Couldn't find entry.");

            }
        } while(!flag);

    }

    public void updateMatchScore(int gameScore)
    {
        if (!turn.returnTurn())
            matchScore[0] += gameScore;
        else
            matchScore[1] += gameScore;
        System.out.println("Current Match Score: " + IDs.returnName(turn.returnOrientation()) + " " + matchScore[0] + " - " + IDs.returnName(!turn.returnOrientation()) + " " + matchScore[1]);
    }

    public void announceGameResult(int gameScore)
    {
        String resultMessage;
        if (gameScore == 3) {
            resultMessage = "Game ends in a Backgammon! " + IDs.returnName(turn.returnTurn()) + " wins 3 points.";
        } else if (gameScore == 2) {
            resultMessage = "Game ends in a Gammon! " + IDs.returnName(turn.returnTurn() ) + " wins 2 points.";
        } else {
            resultMessage = "Game ends in a Single! " + IDs.returnName(turn.returnTurn() ) + " wins 1 point.";
        }
        System.out.println(resultMessage);
    }

    public void displayMatchInfo()
    {
        System.out.println("Match Length: " + matchLength);
        System.out.println("Current Match Score: " + IDs.returnName(turn.returnOrientation()) + " " + matchScore[0] + " - " + IDs.returnName(!turn.returnOrientation()) + " " + matchScore[1]);
    }
}
