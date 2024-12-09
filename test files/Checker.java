public class Checker
{
    // false for white, true for brown
    private boolean colour;
    private String display;

    public Checker(Boolean colour)
    {
        this.colour = colour;
        if(colour)
            this.display = "B";
        else
            this.display = "W";
    }

    public Boolean returnColour() { return this.colour; }
    public String returnString() { return this.display; }


}