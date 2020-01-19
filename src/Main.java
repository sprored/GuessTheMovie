import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {
    /*
    The computer randomly picks a movie title, and shows you how many letters it's made up of.
Your goal is to try to figure out the movie by guessing one letter at a time.
If a letter is indeed in the title the computer will reveal its correct position
 in the word, if not, you lose a point. If you lose 10 points, game over!

Once the computer picks a random title, it will display underscores "_" in place of the
real letters, thereby only giving away the number of letters in the movie title.
Then it will wait for the player to enter their first letter guess.
If the letter was indeed in the word, the underscores "_" that match that letter will be
replaced with the correct letter revealing how many letters have matched their guess
and where they are.
If their guess isn't in the movie title, then the output will display the same output
as the previous step as well as any letters that have been previously guessed wrong.
Eventually, if the player manages to guess all the letters in the movie title correctly
before they lost 10 points, they win
     */
    public static void main(String[] args) {
        /*
         **************** VARIABLES **************
         */
        final String FILE1 = "movies.txt";
        final String FILE2 = "PrideAndPrejudice.txt";
        File file = new File(FILE1);
        String line = "";
        int lineCount = 0;
        char userChar = ' ';

        // First count the number of lines in the file
        lineCount = countLinesInFile(file);

        // Then select a random number between 1 and lineCount
        int rndLineNum = (int) (Math.random() * lineCount + 1);

        // look for the random line in the file
        // Snippet for large files
        String toGuess = extractLine(FILE1, rndLineNum);

        // new variable with asterisks instead of words
        char[] starsToGuess = transformLine(toGuess);
        for (int i = 0; i < starsToGuess.length; i++) {
            System.out.print(starsToGuess[i]);
        }
        System.out.println("");

        // Ask for a character
        userChar = askForACharacter();

        // if the character is present display it
        // else count one error
        starsToGuess = starToLetter(toGuess, userChar);
        for (int i = 0; i < starsToGuess.length; i++) {
            System.out.print(starsToGuess[i]);
        }

        System.out.println("linCount = " + lineCount);
        System.out.println("Random num = " + rndLineNum);
        //System.out.println(toGuess);

    }

    /*
     ********************************************************************
     *                            METHODS
     ********************************************************************
     */

    public static char [] starToLetter(String str, char chr) {
        char [] xxx
        return ;
    }

    /**
     * Extract a line from the file
     * @param FILE
     * @param lineNum
     * @return
     */
    public static String extractLine(String FILE, int lineNum) {
        try (Stream<String> all_lines = Files.lines(Paths.get(FILE))) {
            return all_lines.skip(lineNum - 1).findFirst().get();
        } catch (IOException exception) {
            exception.printStackTrace();
            return "Ther has been some problem reading the file ... or else.";
        }

    }

    /**
     * count the number of lines in the file
     *
     * @param file
     * @return
     */
    public static int countLinesInFile(File file) {
        int count = 0;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                count++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Ask for a character
     *
     * @return
     */
    public static char askForACharacter() {
        Scanner scanner = new Scanner(System.in);
        String userInput = "";

        System.out.println("Which is your guess?");
        userInput = scanner.nextLine();

        while (userInput.length() != 1) {
            System.out.println("Please, enter a single letter:");
            userInput = scanner.nextLine();
        }
        return userInput.charAt(0);
    }


    /**
     * Transform the line to *
     *
     * @param myLine
     * @return
     */
    public static char[] transformLine(String myLine) {
        char[] charMyLine = myLine.toCharArray();
        char[] starMyLine = charMyLine;

        for (int i = 0; i < myLine.length(); i++) {
            if (charMyLine[i] != ' ') {
                starMyLine[i] = '*';
            }
        }
        return starMyLine;
    }
}
