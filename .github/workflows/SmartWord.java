/*

  Authors (group members):
  Email addresses of group members:
  Group name:

  Course:
  Section:

  Description of the overall algorithm:


*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class SmartWord
{

      String[] guesses = new String[3];  // 3 guesses from SmartWord
      Trie<Character> root = new Trie<Character>();
      String builtWord = "";

      // initialize SmartWord with a file of English words
      public SmartWord (String wordFile) throws FileNotFoundException
      {
         // File file = new File(wordFile);
         // Scanner reader = new Scanner (file);
         // // root element stays null
         // // all children branch off of null node (a-z characters)
         // while (reader.hasNextLine()) {
         //    String line = reader.nextLine().toLowerCase();
         //    root.insert(line);
         // }
      }

      // temp function to test class
      public Trie<Character> getRoot () {
         return this.root;
      }

      // process old messages from oldMessageFile
      public void processOldMessages(String oldMessageFile) throws FileNotFoundException
      {
         File file = new File(oldMessageFile);
         Scanner reader = new Scanner(file);
         while (reader.hasNextLine()) {
            // splits words by whitespace - deals with weird white spacing
            String[] words = reader.nextLine().replaceAll("\\s+", " ").split(" ");
            // iterate through words
            for (int i = 0; i < words.length; i++) {
               // gets rid of numbers and punctuation
               words[i] = words[i].replaceAll("[^a-zA-Z]", "");
               // gets rid of empty strings that occasional occur
               if (words[i].equals("")) {
                  continue;
               }
               // System.out.println(words[i]);
               root.insert(words[i]);
            }
         }
      }

      // based on a letter typed in by the user, return 3 word guesses in an array
      // letter: letter typed in by the user
      // letterPosition:  position of the letter in the word, starts from 0
      // wordPosition: position of the word in a message, starts from 0
      public String[] guess(char letter,  int letterPosition, int wordPosition)
      {
      // System.out.println("Letter: " + letter);
      // System.out.println("Letter Position: " + letterPosition);
      // System.out.println("Word Position: " + wordPosition);
      if (letterPosition == 0) {
         builtWord = "";
         builtWord += letter;
      } else {
         builtWord += letter;
      }
      String guess = root.findClosestWord(builtWord);
      guesses[0] = guess;
      // System.out.println("****************");
      // System.out.println("Made Guess: " + guess);
      // System.out.println("****************");
      return guesses;
      }

      // feedback on the 3 guesses from the user
      // isCorrectGuess: true if one of the guesses is correct
      // correctWord: 3 cases:
      // a.  correct word if one of the guesses is correct
      // b.  null if none of the guesses is correct, before the user has typed in 
      //            the last letter
      // c.  correct word if none of the guesses is correct, and the user has 
      //            typed in the last letter
      // That is:
      // Case       isCorrectGuess      correctWord   
      // a.         true                correct word
      // b.         false               null
      // c.         false               correct word
      public void feedback(boolean isCorrectGuess, String correctWord)        
      {

      }
}
