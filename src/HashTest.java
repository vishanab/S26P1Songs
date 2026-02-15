import java.io.IOException;
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
     * Tests Handle methods
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
     * Tests hash table resizing when capacity is exceeded
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
     * Tests inserting elements in the hash table
     */
    public void testHashInsertFind() throws Exception {
        manager = new MemManager(32);
        table = new Hash(10, manager);
        Handle h1 = new Handle(1, 1);
        table.insert(h1, "Apple");
        assertEquals(1, table.getSize());
    }


    /**
     * Tests inserting and removing elements from the hash table
     */
    public void testHashInsertRemove() throws Exception {
        manager = new MemManager(64);
        table = new Hash(10, manager);
        Handle h1 = manager.insert("Apple".getBytes());
        Handle h2 = manager.insert("Banana".getBytes());
        Handle h3 = manager.insert("Cherry".getBytes());

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
     * Tests that tombstones are handled correctly
     */
    public void testTombstone() throws Exception {
        manager = new MemManager(64);
        table = new Hash(5, manager);
        Handle h1 = manager.insert("A".getBytes());
        Handle h2 = manager.insert("F".getBytes());
        table.insert(h1, "A");
        table.insert(h2, "F");

        table.remove("A");
        assertNull(table.find("A"));
        Handle h3 = manager.insert("K".getBytes());
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
        table.insert(manager.insert("A".getBytes()), "A");
        table.insert(manager.insert("B".getBytes()), "B");
        table.insert(manager.insert("C".getBytes()), "C");
        String result = table.remove("Z");
        assertEquals("", result);
        assertEquals(3, table.getSize());
        assertNotNull(table.find("A"));
        assertNotNull(table.find("B"));
        assertNotNull(table.find("C"));
    }


    /** Tests inserting beyond capacity triggers correct branch */
    public void testInsertICapacityBranch() throws Exception {
        manager = new MemManager(32);
        table = new Hash(4, manager);

        Handle h1 = manager.insert("A".getBytes());
        Handle h2 = manager.insert("B".getBytes());
        Handle h3 = manager.insert("C".getBytes());
        Handle h4 = manager.insert("D".getBytes());
        Handle h5 = manager.insert("E".getBytes());
        assertNull(table.insert(h1, "A"));
        assertNull(table.insert(h2, "B"));
        assertNull(table.insert(h3, "C"));
        assertNull(table.insert(h4, "D"));

        assertNull(table.insert(h5, "E"));
    }


    /**
     * Ensures tombstones are not reinserted during resize
     * 
     * @throws IOException
     */
    public void testResizeDoesNotReinsertDeleted() throws IOException {
        manager = new MemManager(64);
        table = new Hash(4, manager);
        table.insert(manager.insert("A".getBytes()), "A");
        table.insert(manager.insert("B".getBytes()), "B");

        table.remove("A");
        table.insert(manager.insert("C".getBytes()), "C");
        assertNull(table.find("A"));
    }


    /**
     * Ensures find skips tombstones while probing
     * 
     * @throws IOException
     */
    public void testFindSkipsTombstones() throws IOException {

        manager = new MemManager(64);
        table = new Hash(5, manager);
        Handle h1 = manager.insert("A".getBytes());
        Handle h2 = manager.insert("F".getBytes());

        table.insert(h1, "A");
        table.insert(h2, "F");

        table.remove("A");
        assertEquals(h2, table.find("F"));
    }


    /**
     * Tests that the hash multiplier resets correctly
     */
    public void testHashMultiplierResetsCorrectly() {
        manager = new MemManager(64);
        table = new Hash(100, manager);

        String testStr = "aaaaa";
        int hash1 = table.h(testStr, Integer.MAX_VALUE);
        long expectedSum = 'a' * 1L + 'a' * 256L + 'a' * 65536L + 'a'
            * 16777216L + 'a' * 1L;
        int expected = (int)(Math.abs(expectedSum) % Integer.MAX_VALUE);

        assertEquals(expected, hash1);

        String testStr2 = "abcdefgh";
        int hash2 = table.h(testStr2, Integer.MAX_VALUE);
        assertTrue(hash2 >= 0);
    }


    /**
     * Tests equality checks for find
     * 
     * @throws IOException
     */
    public void testFindEqualityChecks() throws IOException {
        manager = new MemManager(128);
        table = new Hash(10, manager);

        Handle h1 = manager.insert("Test".getBytes());
        Handle h2 = manager.insert("Testing".getBytes());
        Handle h3 = manager.insert("Tester".getBytes());

        table.insert(h1, "Test");
        table.insert(h2, "Testing");
        table.insert(h3, "Tester");

        Handle found1 = table.find("Test");
        Handle found2 = table.find("Testing");
        Handle found3 = table.find("Tester");

        assertEquals(h1, found1);
        assertEquals(h2, found2);
        assertEquals(h3, found3);
        Handle notFound = table.find("TestXYZ");
        assertNull(notFound);
        table.remove("Testing");
        assertNull(table.find("Testing"));
        assertNotNull(table.find("Test"));
        assertNotNull(table.find("Tester"));
    }


    /**
     * Tests quadratic probing functionality
     * 
     * @throws IOException
     * 
     */
    public void testFindQuadraticProbing() throws IOException {
        manager = new MemManager(128);
        table = new Hash(5, manager);
        Handle h1 = manager.insert("Alpha".getBytes());
        Handle h2 = manager.insert("Beta".getBytes());
        Handle h3 = manager.insert("Gamma".getBytes());
        Handle h4 = manager.insert("Delta".getBytes());

        table.insert(h1, "Alpha");
        table.insert(h2, "Beta");
        table.insert(h3, "Gamma");
        table.insert(h4, "Delta");

        assertEquals(h1, table.find("Alpha"));
        assertEquals(h2, table.find("Beta"));
        assertEquals(h3, table.find("Gamma"));
        assertEquals(h4, table.find("Delta"));

        table.remove("Beta");

        assertEquals(h1, table.find("Alpha"));
        assertEquals(h3, table.find("Gamma"));
        assertEquals(h4, table.find("Delta"));
        assertNull(table.find("Beta"));

        Handle h5 = manager.insert("Epsilon".getBytes());
        table.insert(h5, "Epsilon");
        assertEquals(h5, table.find("Epsilon"));
    }


    /**
     * Tests all hash operations in complex scenarios
     * 
     * @throws IOException
     */
    public void testComplex() throws IOException {
        manager = new MemManager(256);
        table = new Hash(8, manager);

        Handle h1 = manager.insert("One".getBytes());
        Handle h2 = manager.insert("Two".getBytes());
        Handle h3 = manager.insert("Three".getBytes());
        Handle h4 = manager.insert("Four".getBytes());
        Handle h5 = manager.insert("Five".getBytes());

        table.insert(h1, "One");
        table.insert(h2, "Two");
        table.insert(h3, "Three");
        table.insert(h4, "Four");

        assertEquals(4, table.getSize());

        table.remove("Two");
        table.remove("Four");
        assertEquals(2, table.getSize());

        table.insert(h5, "Five");
        Handle h6 = manager.insert("Six".getBytes());
        table.insert(h6, "Six");

        assertEquals(4, table.getSize());

        assertNotNull(table.find("One"));
        assertNull(table.find("Two"));
        assertNotNull(table.find("Three"));
        assertNull(table.find("Four"));
        assertNotNull(table.find("Five"));
        assertNotNull(table.find("Six"));

        Handle h7 = manager.insert("Seven".getBytes());
        table.insert(h7, "Seven");

        assertNotNull(table.find("One"));
        assertNotNull(table.find("Three"));
        assertNotNull(table.find("Five"));
        assertNotNull(table.find("Six"));
        assertNotNull(table.find("Seven"));
        assertEquals(5, table.getSize());
    }


    /**
     * Tests mutations for the remove
     * 
     * @throws IOException
     */
    public void testRemoveEqualityCheck() throws IOException {
        manager = new MemManager(256);
        table = new Hash(10, manager);

        Handle h1 = manager.insert("TestA".getBytes());
        Handle h2 = manager.insert("TestB".getBytes());
        Handle h3 = manager.insert("TestC".getBytes());

        table.insert(h1, "TestA");
        table.insert(h2, "TestB");
        table.insert(h3, "TestC");

        table.remove("TestB");
        assertNotNull("TestA should still exist", table.find("TestA"));
        assertNull("TestB should be removed", table.find("TestB"));
        assertNotNull("TestC should still exist", table.find("TestC"));
        assertEquals(2, table.getSize());
    }


    /**
     * Tests prevention of infinite loop on insertion
     * 
     * @throws IOException
     */
    public void testInfiniteLoopPrevention() throws IOException {
        manager = new MemManager(256);
        table = new Hash(3, manager);

        Handle h1 = manager.insert("A".getBytes());
        table.insert(h1, "A");

        Handle h2 = manager.insert("B".getBytes());
        table.insert(h2, "B");
        assertTrue("Test completed without hanging", true);
        assertTrue(table.getCapacity() >= 6);
    }

}
