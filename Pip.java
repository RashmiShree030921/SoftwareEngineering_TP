import java.util.Vector;

public class Pip
{
    private Vector<Checker> Checkers = new Vector<Checker>();

    public Pip(){}

    public boolean isEmpty() { return Checkers.isEmpty(); }
    public int returnLength() { return Checkers.size(); }
    public boolean returnColour() { return Checkers.getFirst().returnColour();}

    //colour variable is the colour of the source pip
    //this checks whether the destination pip is blocked
    public boolean blockCheck(Boolean colour)
    {
        if(!this.isEmpty())
            if(colour != Checkers.getFirst().returnColour())
                return (this.returnLength() >1);
        return false;
    }

    //colour variable is the colour of the source pip
    //this checks whether there is a checker to hit on the destination pip
    public boolean hitCheck(Boolean colour)
    {
        if(!this.isEmpty())
            return((Checkers.getFirst().returnColour() != colour) && (this.returnLength() ==1));
        return false;
    }

    public void addChecker(Checker select)
    {
        if(select !=null)
            Checkers.add(select);
    }

    public Checker removeChecker()
    {
        if(!Checkers.isEmpty())
        {
            return Checkers.removeLast();
        }
        return null;
    }


    public String Display()
    {
        String output = String.valueOf(this.returnLength());
        if(!this.isEmpty())
            output = output + Checkers.getFirst().returnString();

        return output;
    }

}
