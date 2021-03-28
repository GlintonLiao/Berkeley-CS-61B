import static org.junit.Assert.*;
import org.junit.Test;

public class FlikTest {

    @Test
    public void testFlik() {
        int i = 0;
        int j = 0;
        while (i <= 500) {
            assertTrue(Flik.isSameNumber(i, j));
            i += 1;
            j += 1;
            System.out.println(i);
        }

    }
}