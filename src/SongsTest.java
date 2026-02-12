import student.TestCase;

/**
 * @author CS3114/5040 Staff
 * @version December 2025
 */
public class SongsTest extends TestCase {
    private Songs it;

    /**
     * Sets up the tests thsat follow. In general, used for initialization
     */
    public void setUp() {
        // Nothing to do
    }


    // ----------------------------------------------------------
    /**
     * Test various bad inputs
     *
     * @throws Exception
     */

    public void testBadInput() throws Exception {
        it = new SongsDB();
        assertFalse(it.clear()); // Not been initialized yet
        assertFuzzyEquals("Initial hash table size must be positive", it.create(
            -1, 32));
        assertFuzzyEquals("Initial memory manager size must be positive", it
            .create(10, 0));
        assertFuzzyEquals("Initial memory manager size must be a power of 2", it
            .create(10, 3));

        assertFuzzyEquals("Database not initialized", it.insert("a", "b"));
        assertFuzzyEquals("Database not initialized", it.remove("song", "a"));
        assertFuzzyEquals("Database not initialized", it.print("blocks"));
        it.create(32, 32);
        assertFuzzyEquals("Bad print parameter", it.print("dum"));
        assertFuzzyEquals("Bad type value |Dum| on remove", it.remove("Dum",
            "Dum"));
        assertFuzzyEquals("Input strings cannot be null or empty", it.print(
            ""));
        assertFuzzyEquals("Input strings cannot be null or empty", it.print(
            null));

        assertFuzzyEquals("Input strings cannot be null or empty", it.insert("",
            "b"));
        assertFuzzyEquals("Input strings cannot be null or empty", it.insert(
            null, "b"));
        assertFuzzyEquals("Input strings cannot be null or empty", it.insert(
            "a", ""));
        assertFuzzyEquals("Input strings cannot be null or empty", it.insert(
            "a", null));

        assertFuzzyEquals("Input strings cannot be null or empty", it.remove(
            "song", ""));
        assertFuzzyEquals("Input strings cannot be null or empty", it.remove(
            "song", null));
        assertFuzzyEquals("Input strings cannot be null or empty", it.remove("",
            "a"));
        assertFuzzyEquals("Input strings cannot be null or empty", it.remove(
            null, "a"));
    }


    // ----------------------------------------------------------
    /**
     * Test various uses of empty or missing data
     *
     * @throws Exception
     */
    public void testEmpty() throws Exception {
        it = new SongsDB();
        it.create(10, 32);

        assertFuzzyEquals("total artists: 0", it.print("artist"));
        assertFuzzyEquals("total songs: 0", it.print("song"));
        it.insert("Hello World", "Hello World2");
        assertFuzzyEquals("No free blocks are available.", it.print("blocks"));
        assertFuzzyEquals("|Dum| does not exist in the Artist database", it
            .remove("artist", "Dum"));
        assertFuzzyEquals("|Dum| does not exist in the song database", it
            .remove("song", "Dum"));
    }


    // ----------------------------------------------------------
    /**
     * Show output formats
     *
     * @throws Exception
     */

    public void testClear() throws Exception {
        it = new SongsDB();
        it.create(10, 32);
        it.insert("Bob", "SongName");
        it.clear();
        assertFuzzyEquals("|SongName| does not exist in the Song database", it
            .remove("song", "SongName"));
    }


    public void testDuplicates() throws Exception {
        it = new SongsDB();
        it.create(10, 32);
        it.insert("A", "B");
        assertFuzzyEquals(
            "|A| duplicates a record already in the Artist database\r\n"
                + "|C| is added to the Song database", it.insert("A", "C"));
    }


    // ----------------------------------------------------------
    /**
     * Show output formats
     *
     * @throws Exception
     */

    public void testSampleInput() throws Exception {
        it = new SongsDB();
        it.create(10, 32);

        assertFuzzyEquals(
            "|When Summer's Through| does not exist in the Song database", it
                .remove("song", "When Summer's Through"));
        assertFuzzyEquals(
            "|Blind Lemon Jefferson| is added to the Artist database\r\n"
                + "Memory pool expanded to be 64 bytes\r\n"
                + "|Long Lonesome Blues| is added to the Song database", it
                    .insert("Blind Lemon Jefferson", "Long Lonesome Blues"));
        assertFuzzyEquals("Memory pool expanded to be 128 bytes\r\n"
            + "|Ma Rainey| is added to the Artist database\r\n"
            + "|Ma Rainey's Black Bottom| is added to the Song database", it
                .insert("Ma Rainey", "Ma Rainey's Black Bottom"));
        assertFuzzyEquals("|Charley Patton| is added to the Artist database\r\n"
            + "Memory pool expanded to be 256 bytes\r\n"
            + "|Mississippi Boweavil Blues| is added to the Song database", it
                .insert("Charley Patton", "Mississippi Boweavil Blues"));
        assertFuzzyEquals(
            "|Sleepy John Estes| is added to the Artist database\r\n"
                + "|Street Car Blues| is added to the Song database", it.insert(
                    "Sleepy John Estes", "Street Car Blues"));
        assertFuzzyEquals("|Bukka White| is added to the Artist database\r\n"
            + "|Fixin' To Die Blues| is added to the Song database", it.insert(
                "Bukka White", "Fixin' To Die Blues"));
        assertFuzzyEquals("0: |Blind Lemon Jefferson|\r\n"
            + "1: |Sleepy John Estes|\r\n" + "4: |Charley Patton|\r\n"
            + "5: |Bukka White|\r\n" + "7: |Ma Rainey|\r\n"
            + "total artists: 5", it.print("artist"));
        assertFuzzyEquals("1: |Fixin' To Die Blues|\r\n"
            + "2: |Mississippi Boweavil Blues|\r\n"
            + "5: |Long Lonesome Blues|\r\n"
            + "6: |Ma Rainey's Black Bottom|\r\n" + "9: |Street Car Blues|\r\n"
            + "total songs: 5", it.print("song"));
        assertFuzzyEquals("Memory pool expanded to be 512 bytes\r\n"
            + "Artist hash table size doubled\r\n"
            + "|Guitar Slim| is added to the Artist database\r\n"
            + "Song hash table size doubled\r\n"
            + "|The Things That I Used To Do| is added to the Song database", it
                .insert("Guitar Slim", "The Things That I Used To Do"));
        assertFuzzyEquals(
            "|Style Council| does not exist in the Artist database", it.remove(
                "artist", "Style Council"));
        assertFuzzyEquals("|Ma Rainey| is removed from the Artist database", it
            .remove("artist", "Ma Rainey"));
        assertFuzzyEquals(
            "|Mississippi Boweavil Blues| is removed from the Song database", it
                .remove("song", "Mississippi Boweavil Blues"));
        assertFuzzyEquals(
            "|(The Best Part Of) Breakin' Up| does not exist in the Song database",
            it.remove("song", "(The Best Part Of) Breakin' Up"));
        assertFuzzyEquals("16: 64 272\r\n" + "32: 128\r\n" + "64: 320\r\n"
            + "128: 384", it.print("blocks"));
        assertFuzzyEquals(
            "|Blind Lemon Jefferson| duplicates a record already in the Artist database\r\n"
                + "|Got The Blues| is added to the Song database", it.insert(
                    "Blind Lemon Jefferson", "Got The Blues"));
        assertFuzzyEquals("|Little Eva| is added to the Artist database\r\n"
            + "|The Loco-Motion| is added to the Song database", it.insert(
                "Little Eva", "The Loco-Motion"));
        assertFuzzyEquals("0: |Blind Lemon Jefferson|\r\n"
            + "4: |Bukka White|\r\n" + "7: TOMBSTONE\r\n"
            + "10: |Sleepy John Estes|\r\n" + "12: |Guitar Slim|\r\n"
            + "14: |Charley Patton|\r\n" + "18: |Little Eva|\r\n"
            + "total artists: 6", it.print("artist"));
        assertFuzzyEquals("1: |Fixin' To Die Blues|\r\n" + "2: TOMBSTONE\r\n"
            + "5: |Street Car Blues|\r\n" + "8: |Got The Blues|\r\n"
            + "15: |Long Lonesome Blues|\r\n"
            + "16: |Ma Rainey's Black Bottom|\r\n"
            + "17: |The Things That I Used To Do|\r\n"
            + "18: |The Loco-Motion|\r\n" + "total songs: 7", it.print("song"));
        assertFuzzyEquals("|Jim Reeves| is added to the Artist database\r\n"
            + "|Jingle Bells| is added to the Song database", it.insert(
                "Jim Reeves", "Jingle Bells"));
        assertFuzzyEquals(
            "|Mongo Santamaria| is added to the Artist database\r\n"
                + "|Watermelon Man| is added to the Song database", it.insert(
                    "Mongo Santamaria", "Watermelon Man"));
        assertFuzzyEquals("16: 368\r\n" + "128: 384", it.print("blocks"));
    }


    public void testMemManagerEdgeSplit() throws Exception {
        it = new SongsDB();
        it.create(4, 8);
        it.insert("A", "1234");
        it.insert("B", "5678");
        it.insert("C", "abcd");
        String blocks = it.print("blocks");
        assertNotNull(blocks);
    }

/*
 * public void testInsertMany() throws Exception {
 * it = new SongsDB();
 * it.create(4, 16);
 * 
 * for (int i = 0; i < 20; i++) {
 * it.insert("Artist" + i, "Song" + i);
 * }
 * 
 * assertTrue(it.print("artist").contains("total artists"));
 * }
 */


    public void testHashResize() throws Exception {
        it = new SongsDB();
        it.create(2, 64);
        it.insert("A", "1");
        it.insert("B", "2");
        String artists = it.print("artist");

        assertTrue(artists.contains("total artists: 2"));
    }


    public void testProbeAfterDelete() throws Exception {
        it = new SongsDB();
        it.create(4, 64); // tiny hash forces collisions

        it.insert("Aa", "1"); // "Aa" and "BB" collide in many sfold variants
        it.insert("BB", "2");

        it.remove("artist", "Aa");

        // If probing is wrong, this lookup fails
        String result = it.print("artist");

        assertTrue(result.contains("|BB|"));
    }

/*
 * public void testExpandThenImmediateInsert() throws Exception {
 * it = new SongsDB();
 * it.create(10, 16);
 * for (int i = 0; i < 20; i++) {
 * it.insert("Artist" + i, "Song" + i);
 * }
 * String result = it.insert("FinalArtist", "FinalSong");
 * 
 * assertTrue(result.contains("FinalSong"));
 * }
 */


    public void testRemoveAllThenAllocateHuge() throws Exception {
        it = new SongsDB();
        it.create(10, 64);

        for (int i = 0; i < 10; i++) {
            it.insert("A" + i, "BBBBBBBB"); // same size blocks
        }

        for (int i = 0; i < 10; i++) {
            it.remove("song", "BBBBBBBB");
        }

        // Should merge into one giant block
        String res = it.insert("BIG", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

        assertTrue(res.contains("BIG"));
    }


    public void testTombstoneProbeChain() throws Exception {
        it = new SongsDB();
        it.create(4, 64); // tiny table forces collisions

        it.insert("AA", "1");
        it.insert("BB", "2");
        it.insert("CC", "3");

        it.remove("artist", "AA"); // leaves tombstone

        // This MUST probe past tombstone
        it.insert("DD", "4");

        String print = it.print("artist");

        assertTrue(print.contains("DD"));
    }


    public void testSplitCascade() throws Exception {
        it = new SongsDB();
        it.create(10, 128);

        // one big allocation
        it.insert("Huge", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

        // now many tiny allocations
        for (int i = 0; i < 10; i++) {
            it.insert("Tiny" + i, "A");
        }

        assertTrue(it.print("artist").contains("Tiny9"));
    }


    public void testCheckerboardFragmentation() throws Exception {
        it = new SongsDB();
        it.create(10, 128);

        for (int i = 0; i < 8; i++) {
            it.insert("A" + i, "BBBBBBBB");
        }

        // remove every other
        for (int i = 0; i < 8; i += 2) {
            it.remove("song", "BBBBBBBB");
        }

        // requires merges across gaps
        String res = it.insert("Large", "XXXXXXXXXXXXXXXX");

        assertTrue(res.contains("Large"));
    }


    public void testRemoveThenReinsertSame() throws Exception {
        it = new SongsDB();
        it.create(10, 64);

        it.insert("Artist", "Song");
        it.remove("song", "Song");

        String res = it.insert("Artist2", "Song");

        assertTrue(res.contains("Song"));
    }


    public void testPrintAfterManyRemoves() throws Exception {
        it = new SongsDB();
        it.create(10, 64);

        for (int i = 0; i < 10; i++) {
            it.insert("A" + i, "S" + i);
        }

        for (int i = 0; i < 10; i++) {
            it.remove("artist", "A" + i);
        }

        String out = it.print("artist");

        String expected = "1: TOMBSTONE\n" + "2: TOMBSTONE\n" + "5: TOMBSTONE\n"
            + "6: TOMBSTONE\n" + "9: TOMBSTONE\n" + "10: TOMBSTONE\n"
            + "13: TOMBSTONE\n" + "14: TOMBSTONE\n" + "17: TOMBSTONE\n"
            + "18: TOMBSTONE\n" + "total artists: 0";
        assertEquals(expected.trim(), out.trim());
    }

/*
 * public void testMassInsert() throws Exception {
 * it = new SongsDB();
 * it.create(16, 32);
 * 
 * for (int i = 0; i < 200; i++) {
 * it.insert("Artist" + i, "Song" + i);
 * }
 * 
 * assertTrue(it.print("artist").contains("Artist199"));
 * }
 */


    public void testResizeWithTombstones() throws Exception {
        it = new SongsDB();
        it.create(4, 64);

        it.insert("A", "1");
        it.insert("B", "2");
        it.insert("C", "3");

        it.remove("artist", "B");
        it.insert("D", "4");
        it.insert("E", "5");

        String table = it.print("artist");

        assertTrue(table.contains("A"));
        assertTrue(table.contains("D"));
    }


    public void testMergeThenSplit() throws Exception {
        it = new SongsDB();
        it.create(10, 32);

        it.insert("A", "AAAA");
        it.insert("B", "BBBB");

        it.remove("song", "AAAA");
        it.remove("song", "BBBB");
        it.remove("artist", "A"); // ADD THIS
        it.remove("artist", "B"); // ADD THIS

        String blocks = it.print("blocks");
        assertFalse(blocks.contains("4 "));
    }


    public void testMergeAcrossMultipleLevels() throws Exception {
        it = new SongsDB();
        it.create(10, 32);

        it.insert("A", "AAAA");
        it.insert("B", "BBBB");
        it.insert("C", "CCCCCCCC");

        it.remove("song", "AAAA");
        it.remove("song", "BBBB");
        it.remove("song", "CCCCCCCC");
        it.remove("artist", "A"); // ADD THIS
        it.remove("artist", "B"); // ADD THIS
        it.remove("artist", "C"); // ADD THIS

        String blocks = it.print("blocks");
        assertFalse(blocks.contains("4 "));
        assertFalse(blocks.contains("8 "));
    }


    public void testMergeSplitMergeAgain() throws Exception {
        it = new SongsDB();
        it.create(10, 32);

        it.insert("A", "AAAA");
        it.insert("B", "BBBB");

        it.remove("song", "AAAA");
        it.remove("song", "BBBB");
        it.remove("artist", "A"); // ADD THIS
        it.remove("artist", "B"); // ADD THIS

        it.insert("C", "CCCCCCCCCCCCCCCC");

        it.remove("song", "CCCCCCCCCCCCCCCC");
        it.remove("artist", "C"); // ADD THIS

        String blocks = it.print("blocks");
        assertFalse(blocks.contains("4 "));
        assertFalse(blocks.contains("8 "));
        assertFalse(blocks.contains("16 "));
    }


    public void testReverseRemovalMerge() throws Exception {
        it = new SongsDB();
        it.create(10, 32);

        it.insert("A", "AAAA");
        it.insert("B", "BBBB");

        it.remove("song", "BBBB");
        it.remove("song", "AAAA");
        it.remove("artist", "B"); // ADD THIS
        it.remove("artist", "A"); // ADD THIS

        String blocks = it.print("blocks");
        assertFalse(blocks.contains("4 "));
    }


    public void testExpansionThenMerge() throws Exception {
        it = new SongsDB();
        it.create(10, 32);

        it.insert("A", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        it.remove("song", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        it.remove("artist", "A"); // ADD THIS

        String blocks = it.print("blocks");
        assertFalse(blocks.contains("32 32"));
    }


    public void testRemoveThenInsertSameName() throws Exception {
        it = new SongsDB();
        it.create(10, 32);

        it.insert("Artist", "Song");
        it.remove("song", "Song");

        it.insert("Artist2", "Song");

        String table = it.print("song");

        assertTrue(table.contains("Song"));
    }


    public void testFullCollapse() throws Exception {
        it = new SongsDB();
        it.create(10, 64);

        for (int i = 0; i < 8; i++) {
            it.insert("A" + i, "BBBB");
        }

        for (int i = 0; i < 8; i++) {
            it.remove("song", "BBBB");
        }

        String blocks = it.print("blocks");

        assertFalse(blocks.matches(".*(4|8|16|32).*"));
    }


    public void testInsertNullArtistAndSong() throws Exception {
        it = new SongsDB();
        it.create(10, 32);
        assertFuzzyEquals("Input strings cannot be null or empty", it.insert(
            null, null));
        assertFuzzyEquals("Input strings cannot be null or empty", it.insert(
            null, "s"));
        assertFuzzyEquals("Input strings cannot be null or empty", it.insert(
            "a", null));
        assertFuzzyEquals("Input strings cannot be null or empty", it.insert("",
            ""));
        assertFuzzyEquals("Input strings cannot be null or empty", it.insert("",
            "s"));
        assertFuzzyEquals("Input strings cannot be null or empty", it.insert(
            "a", ""));
    }

}
