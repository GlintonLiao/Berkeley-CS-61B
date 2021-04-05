import java.util.ArrayList;
import java.util.List;

public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> newDeque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i += 1) {
            Character wordToAdd = word.charAt(i);
            newDeque.addLast(wordToAdd);
        }
        return newDeque;
    }

    public boolean isPalindrome(String word) {

        /** Classic Method
        StringBuilder str = new StringBuilder(word);
        String newStr = str.reverse().toString();
        return newStr.equals(word);
         */

        //Method for the course
        Deque<Character> newDeque = wordToDeque(word);

        if (newDeque.size() == 1 || newDeque.size() == 0) {
            return true;
        } else {
            if(newDeque.removeFirst() == newDeque.removeLast()) {
                return isPalindrome(dequeToString(newDeque));
            } else {
                return false;
            }
        }
    }


    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> newDeque = wordToDeque(word);

        if (newDeque.size() == 0 || newDeque.size() == 1) {
            return true;
        } else {
            if (newDeque.removeFirst() == newDeque.removeLast()) {
                return isPalindrome(dequeToString(newDeque), cc);
            } else {
                return false;
            }
        }
    }

    //Helper function
    private String dequeToString(Deque d) {
        String newString = "";
        while (d.size() != 0) {
            newString += d.removeFirst();
        }
        return newString;
    }

}
