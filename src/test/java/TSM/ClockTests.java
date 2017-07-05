import org.junit.*;
import static org.junit.Assert.*;

import javax.swing.JLabel;

public class ClockTests {
    @Test
    public void testClock() {
        JLabel testLabel = new JLabel("test");
        Clock test = new Clock(testLabel);
        assertEquals(test.getElapsedTime(), "<html><b>Elapsed Time: </b>00:00:00</html>");
    }

    @Test
    public void testClock2() {
        JLabel testLabel = new JLabel("test");
        Clock test = new Clock(testLabel, 1, 2, 3);
        assertEquals(test.getElapsedTime(), "<html><b>Elapsed Time: </b>01:02:03</html>");
    }
}
