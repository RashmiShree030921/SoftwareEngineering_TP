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
            System.out.println("Couldn't find file 'Command_Test.txt' in your directory. Please ensure the file has the correct name.");
        }

    }

    public String readNext()
    {
        String line;
        try {
            if (input.hasNextLine()) {
                line = input.nextLine().trim().toLowerCase();
                System.out.println(line);
                return line;
            }
        }
        catch(Exception ex)
        {
            System.out.println("Error occurred in reading the file.");
            System.exit(0);

        }
        System.out.println("End of file reached.");
        System.exit(0);
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
                input.nextLine();
                return test;
            }
        }
        catch(Exception ex) {
            System.out.println("Error occurred in reading the file.");

        }
        System.out.println("End of file reached.");
        System.exit(0);
        return 0;
    }

}