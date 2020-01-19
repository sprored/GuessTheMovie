import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {
  /*
  The computer randomly picks a movie title, and shows you how many letters it's made up of.
Your goal is to try to figure out the movie by guessing one letter at a time.
If a letter is indeed in the title the computer will reveal its correct position
in the word, if not, you lose a point. If you lose 10 points, game over!
   */
  public static void main(String[] args) {
    /*
     **************** VARIABLES **************
     */
    final String FILE1 = "movies.txt";
    final String FILE2 = "100best.txt";
    File file = new File(FILE1);
    //String line = "";

    // First count the number of lines in the file
    int lineCount = countLinesInFile(file);

    // Then select a random number between 1 and lineCount
    int rndLineNum = (int) (Math.random() * lineCount + 1);

    // look for the random line in the file
    String lineToGuess = extractLine(FILE1, rndLineNum);

    System.out.println("");

    char[] starsToGuess = transformLine(lineToGuess);
    for (int i = 0; i < starsToGuess.length; i++) {
      System.out.print(starsToGuess[i]);
    }
    System.out.println("");

    gameCoreLoop(starsToGuess, lineToGuess);
  }

  /*
   ********************************************************************
   *                            METHODS
   ********************************************************************
   */

  /**
   * * - ask for a character
   * - find if it exists in the line
   * - if it does exist, change the corresponding stars in the line
   * - if it doesn't, increase the counter of failures and store the letter
   *
   * @param starsToGuess
   * @param toGuess
   */
  public static void gameCoreLoop(char[] starsToGuess, String toGuess) {
    // Ask for a character
    int failures = 0;
    int remainingAttempts = 10;
    String wrongLetters = "";

    while ( !(Arrays.equals(starsToGuess, toGuess.toCharArray()) )&&
            !(remainingAttempts == 0)) {
      char userChar = askForACharacter();

      // if the character is present display it
      // else count one error
      char[] newStarsToGuess = starToLetter(toGuess, starsToGuess, userChar);
      if (Arrays.equals(starsToGuess, newStarsToGuess)) {
        failures++;
        wrongLetters += userChar;
        remainingAttempts--;
        System.out.println("WRONG!");
      } else {
        for (int i = 0; i < starsToGuess.length; i++) {
          starsToGuess[i] = newStarsToGuess[i];
        }
        System.out.println("RIGHT!");
      }

      System.out.println("");
      System.out.println("Letters failed: " + wrongLetters);
      System.out.println("Remaining attempts: " + remainingAttempts);
      System.out.print(starsToGuess);
      System.out.println();
    }

    if (remainingAttempts == 0){
      System.out.println();
      System.out.println();
      System.out.println(" You loose!");
    } else{
      System.out.println();
      System.out.println();
      System.out.println("You WIN!");
    }
  }


  /**
   * Find if the given letter is among the words and display all the occurrences
   *
   * @param oriStr
   * @param withStars
   * @param chr
   * @return
   */
  public static char[] starToLetter(String oriStr, char[] withStars, char chr) {
    char[] oriArray = oriStr.toCharArray();
    char[] myStarsToGuess = new char[oriArray.length];
    for (int i = 0; i < oriArray.length; i++) {
      if (oriArray[i] == chr) {
        myStarsToGuess[i] = chr;
      } else {
        myStarsToGuess[i] = withStars[i];
      }
    }
    return myStarsToGuess;
  }

  /**
   * Extract a line from the file
   *
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
