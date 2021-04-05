import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static CharacterComparator offByN = new OffByN(5);


    @Test
    public void testEqualChars() {
        char a = 'a';
        char b = 'f';
        char c = 'd';

        assertTrue(offByN.equalChars(a, b));
        assertFalse(offByN.equalChars(b, c));
    }


}
