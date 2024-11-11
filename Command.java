public class Command
{
    private int start_pip;
    private int end_pip;
    private final int num_pips =24;

    public Command(int start_pip, int end_pip)
    {
        this.start_pip = start_pip;
        this.end_pip = end_pip;
    }

    public String commandString()
    {
        if(end_pip == -1)
            return (" " + start_pip + "-off" + " ");

        return (" " + start_pip + "-" + end_pip + " ");
    }

    public int returnStart() { return start_pip; }
    public int returnEnd() { return end_pip; }

    public boolean isEqual(Command a) {return (( this.start_pip == a.returnStart()) && ( this.end_pip == a. returnEnd())); }
}
