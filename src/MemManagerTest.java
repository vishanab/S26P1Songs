
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

    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        mem = new MemManager(256);
        t = "Trouble";
        h = mem.insert(t);
    }



    /**
     * tests the insert method
     */
    public void testInsert() {
        assertEquals(h.getSize(), 7);
        //assertEquals(h.getIndex(), 248);
    }


    /**
     * tests the find method
     */
    public void testFind() {
        String res = mem.find(h);
        assertTrue(res.equals("Trouble"));

    }
}
