import student.TestCase;

public class HashTest extends TestCase {
    private Handle h;
    private Hash table;
    private MemManager manager;


    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Nothing to do
    	h = new Handle(1,1);
        manager = new MemManager(32);
    	table = new Hash(10, manager);

    }

    public void testHandle() throws Exception {
        assertFalse(h.getSize() == 0);
        assertEquals(h.getIndex(),1);
        assertEquals(h.getSize(),1);
        assertFalse(h.getIndex() == -1);
    }

    public void testHash() throws Exception {
    	assertEquals(0, table.h("", 32));
    	assertEquals(9, table.h("abc", 10));
    }

}
