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


    /**
     * checks if insert works properly
     */
    public void testHashInsertRemove() throws Exception {
        manager = new MemManager(64);
        table = new Hash(10, manager);
        Handle h1 = manager.insert("Apple");
        Handle h2 = manager.insert("Banana");
        Handle h3 = manager.insert("Cherry");

        assertNull(table.find("Apple"));
        assertNull(table.find("Banana"));

        table.insert(h1, "Apple");
        table.insert(h2, "Banana");
        table.insert(h3, "Cherry");

        assertEquals(h1, table.find("Apple"));
        assertEquals(h2, table.find("Banana"));
        assertEquals(h3, table.find("Cherry"));
        assertEquals(3, table.getSize());

        table.remove("Banana");
        assertNull(table.find("Banana"));
        assertEquals(2, table.getSize());

        assertEquals(h1, table.find("Apple"));
        assertEquals(h3, table.find("Cherry"));

        table.remove("Apple");
        table.remove("Cherry");
        assertEquals(0, table.getSize());
        assertNull(table.find("Apple"));
        assertNull(table.find("Cherry"));
    }


    /**
     * tests that tombstones work
     */
    public void testTombstone() throws Exception {
        manager = new MemManager(64);
        table = new Hash(5, manager);
        Handle h1 = manager.insert("A");
        Handle h2 = manager.insert("F");
        table.insert(h1, "A");
        table.insert(h2, "F");

        table.remove("A");
        assertNull(table.find("A"));
        Handle h3 = manager.insert("K");
        table.insert(h3, "K");
        assertEquals(h3, table.find("K"));
        assertEquals(h2, table.find("F"));
        assertEquals(2, table.getSize());
    }


    /**
     * tests capacity breach
     */
    public void testCapacity() throws Exception {
        manager = new MemManager(128);
        table = new Hash(5, manager);
        table.insert(manager.insert("A"), "A");
        table.insert(manager.insert("B"), "B");
        table.insert(manager.insert("C"), "C");
        String result = table.remove("Z");
        assertEquals("", result);
        assertEquals(3, table.getSize());
        assertNotNull(table.find("A"));
        assertNotNull(table.find("B"));
        assertNotNull(table.find("C"));
    }
}
