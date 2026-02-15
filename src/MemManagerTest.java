
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
        h = mem.insert(t.getBytes());
    }


    /**
     * tests the buddy method
     */
    public void testBuddyMethod() {
        int res = mem.buddyMethod(25);
        assertEquals(res, 5);

    }


    /**
     * tests the insert method
     */
    public void testInsert() {
        assertEquals(h.getSize(), 7);
        // assertEquals(h.getIndex(), 248);
    }


    /**
     * tests the find method
     */
    public void testFind() {
        String res = new String(mem.find(h));
        assertTrue(res.equals("Trouble"));

    }


    /**
     * tests more cases of the buddy method
     */
    public void testBuddyMethod2() {
        assertEquals(0, mem.buddyMethod(1));
        assertEquals(1, mem.buddyMethod(2));
        assertEquals(2, mem.buddyMethod(3));
        assertEquals(2, mem.buddyMethod(4));
        assertEquals(3, mem.buddyMethod(5));
        assertEquals(3, mem.buddyMethod(8));
        assertEquals(7, mem.buddyMethod(128));
        assertEquals(8, mem.buddyMethod(256));
    }

}
