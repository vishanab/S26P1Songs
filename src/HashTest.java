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
        h = new Handle(1, 1);
        manager = new MemManager(32);
        table = new Hash(10, manager);

    }


    /**
     * tests the handle class
     */
    public void testHandle() throws Exception {
        assertFalse(h.getSize() == 0);
        assertEquals(h.getIndex(), 1);
        assertEquals(h.getSize(), 1);
        assertFalse(h.getIndex() == -1);
    }


    /**
     * tests if the hash function/quad probing works
     */
    public void testHash() throws Exception {
        assertEquals(0, table.h("", 32));
        assertEquals(9, table.h("abc", 10));
        assertTrue(table.h("LongerStringForTesting", 50) >= 0);
    }


    /**
     * tests if the hash table resizes
     */
    public void testResize() throws Exception {
        for (int i = 0; i < 5; i++) {
            table.insert(new Handle(i, i), "obj" + i);
        }
        assertEquals(5, table.getSize());
        Handle fin = new Handle(6, 6);
        table.insert(fin, "Fin");
        assertEquals(table.getCapacity(), 20);

    }


    /**
     * checks if insert works properly
     */
    public void testHashInsertFind() throws Exception {
        manager = new MemManager(32);
        table = new Hash(10, manager);
        Handle h1 = new Handle(1, 1);
        table.insert(h1, "Apple");
        assertEquals(1, table.getSize());
        // assertEquals(h1, table.find("Apple"));
    }
}
