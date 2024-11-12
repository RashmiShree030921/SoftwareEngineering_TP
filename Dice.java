import java.util.*;

public class Dice
{
    private ArrayList<Integer> rolls = new ArrayList<Integer>();
    private Random roll = new Random();

    private boolean doubleFlag = false;

    public Dice(ArrayList<Integer> rolls)
    {
        this.rolls = rolls;
    }

    public void rollDice(boolean sort_flag)
    {
       clearDice();

        rolls.add(roll.nextInt(6) + 1);
        rolls.add(roll.nextInt(6) + 1);

        doubleFlag = (Objects.equals(rolls.get(0), rolls.get(1)));

        if (doubleFlag)
        {
            rolls.add(rolls.getFirst());
            rolls.add(rolls.getFirst());
        }

        else
            if(rolls.get(0) < rolls.get(1))
                if(sort_flag)
                    Collections.swap(rolls, 0,1);

    }
    public void clearDice() { rolls.clear(); }
    public ArrayList<Integer> returnRolls() { return rolls; }
    public boolean returnDoubleFlag() { return doubleFlag; }
}
