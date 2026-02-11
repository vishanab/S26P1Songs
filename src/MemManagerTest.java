
// -------------------------------------------------------------------------
/**
 * This is a test case for memory manager
 * 
 * @author Sital Paudel and Vishana Baskaran
 * @version Feb 3, 2026
 */
import student.TestCase;

public class MemManagerTest extends TestCase {
    private MemManager mem;
    private String t;
    private Handle h;

    public void setUp() {
        mem = new MemManager(256);
        t = "Trouble";
        h = mem.insert(t);
    }


    public void testBuddyMethod() {
        int res = mem.buddyMethod(25);
        assertEquals(res, 5);

    }


    public void testInsert() {
        assertEquals(h.getSize(), 7);
        assertEquals(h.getIndex(), 248);
    }


    public void testFind() {
        String res = mem.find(h);
        assertTrue(res.equals("Trouble"));

    }
}
