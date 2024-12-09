import java.util.Scanner;
import java.io.File;


public class TestFile
{
    private String filename = "Command_Test.txt";
    private Scanner input;

    public TestFile()
    {
        try
        {
            File file = new File(filename);
            input = new Scanner(file);
        }
        catch (Exception ex)
        {
            System.out.println("Error");
        }

    }

    public String readNext()
    {
        try {
            if (input.hasNextLine()) {
                String line = input.nextLine();
                return line;
            }
        }
        catch(Exception ex) {
            System.out.println("Couldn't find entry.");

        }
        return null;
    }

    public void closeInput()
    {
        input.close();
    }

    public int nextInt()
    {
        int test;

        try {
            if (input.hasNextInt()) {
                test = input.nextInt();
                return test;
            }
        }
        catch(Exception ex) {
            System.out.println("Couldn't find file 'Command_Test.txt' in your directory. Please ensure it is saved under the correct name.");

        }
        return 0;
    }


}
