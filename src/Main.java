import java.util.Scanner;
import java.io.File;

public class Main {
    public static void main(String args[])
    {
        /**
         * Cambly programming exercise text file to scan
         */
        File cambly = new File("./toRead.txt");

        /**
         *  Try-Catch statement
         *  Instantiate a UniqueWords object
         *  that reads in a file and prints the count of unique words.
         *  Scanner object is created to read in the .txt file
         *  Scanner object is given to UniqueWords object and file is read.
         *  Prints to console and generates a text file
         */
        try
        {
            UniqueWords wordStore = new UniqueWords();
            Scanner scan = new Scanner(cambly);
            wordStore.generateUniqueWords(scan);
        }
        catch (Exception e)
        {
            System.out.print(e);
        }
    }
}
