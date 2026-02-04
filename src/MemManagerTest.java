// -------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 * 
 *  @author sitalpaudel
 *  @version Feb 3, 2026
 */
import student.TestCase;

public class MemManagerTest extends TestCase {
    MemManager mem;
    String t;
    Handle h;

    public void setUp() {
        mem = new MemManager(256);
        t = "Trouble";
        h = mem.insert(t);
    }
    
    public void testBuddyMethod() {
        int res = mem.buddyMethod(25);
        assertEquals(res,5);
        
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
