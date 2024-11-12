public class Turn
{
    private boolean turn;
    private boolean orientation ;

    public Turn()
    {}

    public void setTurn(boolean turn, boolean orientation)
    {
        this.turn = turn;
        this.orientation = orientation;
    }
    public String returnColour()
    {
        if(turn)
            return "B";
        return "W";
    }
    public void changeTurn() { turn = !(turn); }
    public boolean returnTurn() { return turn; }
    public boolean returnOrientation() { return orientation; }

}
