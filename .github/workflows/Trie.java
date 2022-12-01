/*
  Author: Jaden Krekow
  Email: jkrekow2021@my.fit.edu
  Course: CSE2010
  Section: 12
  Description of this file: Creates an trie structure
*/


import java.util.ArrayList;

public class Trie<Character> {
   private char element;
   private Trie<Character> parent;
   private ArrayList<Trie<Character>> children = new ArrayList<Trie<Character>>();
   private boolean isLeaf = true;
   private boolean endOfWord = false;
   
   public Trie () {
      /** Creates Trie initialized with null element
       */
   }

   public Trie (char c) {
      /**
       * Creates a tree starting with the given element to branch off
       * @param e starting element
       */
      this.element = c;
   }

   public void setEnd (boolean ans) {
      /** Set boolean of end of word to know if at that letter a valid word is formed
       * @param ans bool to be set
       */
      this.endOfWord = ans;
   }

   public boolean isEndOfWord () {
      /** returns bool if at end of word or not
       * @return bool if at end of word or not
       */
      return endOfWord;
   }

   public void insert (String word) {
      /** inserts a word into a trie - use root node as this adds characters to children to build word
       * @param word word to be added to trie
       */
      char[] wordArray = word.toCharArray();
      Trie<Character> parent = this;
      ArrayList<Trie<Character>> children = this.children;
      // iterate through all letters in input word
      for (int i = 0; i < word.length(); i++) {
         // if first child just add to children
         if (children.size() == 0) {
            parent.addChild(wordArray[i]);
            if (i == word.length() - 1) {
               children.get(0).setEnd(true);
            }
            parent = children.get(0);
            children = children.get(0).getChildren();
            continue;
         }
         boolean found = false;
         // iterate through characters at this level of a branch in the trie
         for (int j = 0; j < children.size(); j++) {
            // if char is already in tree can skip
            if (wordArray[i] == children.get(j).getElement()) {
               found = true;
               // if last letter of word set endOfWord status
               if (i == word.length() - 1) {
                  if (found) {
                     children.get(j).setEnd(true);
                  }
               }
               parent = children.get(j);
               children = children.get(j).getChildren();
               break;
            }
            // if char is greater lexicographically than previous letter than char must not be in trie
            // since trie is alphabetical
            else if (wordArray[i] > children.get(j).getElement()) {
               continue;
            }
            else {
               // adds to some index of trie
               parent.addChild(j, wordArray[i]);
               found = true;
               // if last letter of word set endOfWord status
               if (i == word.length() - 1) {
                  if (found) {
                     children.get(j).setEnd(true);
                  }
               }
               parent = children.get(j);
               children = parent.getChildren();
               break;
            }
         }
         if (!found) {
            // if char not found then add char because it is not in the trie yet
            parent.addChild(wordArray[i]);
            int size = children.size();
            parent = children.get(size - 1);
            children = children.get(size - 1).getChildren();
         }
      }
   }

   public String findClosestWord (String partialWord) {
      /** finds next complete word lexicographically with inputted partial word
       * @param partialWord inputted partial word
       * @return next complete word lexicographically from partial word
       */
      Trie<Character> parent = this;
      ArrayList<Trie<Character>> children = this.children;
      String builtWord = partialWord;
      // iterate through letters
      for (int i = 0; i < partialWord.length(); i++) {
         char[] letters = partialWord.toCharArray();
         // iterate through children at current parent
         for (int j = 0; j < children.size(); j++) {
            if (letters[i] == children.get(j).getElement()) {
               // match
               // update parent and children
               parent = children.get(j);
               children = parent.getChildren();
            } else if (letters[i] > children.get(j).getElement()) {
               // no match, skip
               continue;
            } else {
               // letter not in trie -> no match for word then
               // return "";
            }
         }
      }

      // all partial letters searched, begin finding nearest lexicographical word
      while (parent != null) {
         // iterate through next possible letters
         for (int i = 0; i < children.size(); i++) {
            // see if any of these letters from depth 1 are the end to a word
            if (children.get(i).isEndOfWord()) {
               builtWord += children.get(i).getElement();
               return builtWord;
            }
            if (children.size() == 0) {
               return "";
            } else {
               builtWord += children.get(0).getElement();
               parent = children.get(0);
               children = parent.getChildren();
            }
         }

         if (children.size() == 0) {
            return builtWord;
         }
      }
      return builtWord;
   }

   /**
    * Returns the element of the root of the tree
    * @return root element of tree
    */
   public char getElement () {
      return this.element;
   }

   /** returns the parent tree of the given node
    * @return parent tree of node
    */
   public Trie<Character> getParent () {
      return this.parent;
   }

   public boolean isLeaf () {
      return this.isLeaf;
   }

   /**
    * Sets the parent of a tree
    * @param parent the inputted parent that it makes the current root the child of
    */
   public void setParent (Trie<Character> parent) {
      this.parent = parent;
   }

   public void setElement (char c) {
      this.element = c;
   }

   public Trie<Character> addChild (char child) {
      /**
       * Adds a child node to the selected node at the end
       * @param child child node that is to be added
       * @return returns child node that was just added
       */
      Trie<Character> childNode = new Trie<Character>(child);
      childNode.setParent(this);
      this.children.add(childNode);
      this.isLeaf = false;
      return childNode;
   }

   public Trie<Character> addChild (int position, char child) {
      /**
       * Adds child at certain pos in children
       * @param position index to be added
       * @param child element to be added
       * @return child trie that was just added
       */
      Trie<Character> childNode = new Trie<Character>(child);
      childNode.setParent(this);
      this.children.add(position, childNode);
      this.isLeaf = false;
      return childNode;
   }

   /**
    * returns the children of the node
    * @return children of the node
    */
   public ArrayList<Trie<Character>> getChildren () {
      return this.children;
   }
}
