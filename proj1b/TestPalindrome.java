import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        String s1 = "racecar";
        assertTrue(palindrome.isPalindrome(s1));
        String s2 = "horse";
        assertFalse(palindrome.isPalindrome(s2));
    }

    @Test
    public void testIsPalindrome2() {
        String s1 = "racecar";
        assertTrue(palindrome.isPalindrome(s1, offByOne));
        String s2 = "horse";
        assertFalse(palindrome.isPalindrome(s2, offByOne));
    }
}
