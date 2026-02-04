import java.io.IOException;

/**
 * The database implementation for this project.
 * We have two hash tables and a memory manager.
 *
 * @author Vishana Baskaran and Sital Paudel
 * @version 1/24/26
 */
public class SongsDB implements Songs {

    private MemManager manager;
    private Hash songTable;
    private Hash artistTable;
    private int memsize;
    private int size;

    // ----------------------------------------------------------
    /**
     * Create a new SongsDB object.
     * But don't set anything -- that gets done by "create"
     */
    public SongsDB() {
    }


    /**
     * Create a brave new World.
     *
     * @param inHash
     *            Initial size for hash tables
     * @param inMemMan
     *            Initial size for the memory manager
     * @return Error messages if appropriate
     */
    public String create(int inHash, int inMemMan) {
        if (inHash < 1) {
            return "Initial hash table size must be positive";
        }
        if (inMemMan < 1) {
            return "Initial memory manager size must be positive";
        }
        if (inMemMan % 2 != 0) {
            return "Initial memory manager size must be a power of 2";
        }
        // create memory manager
        manager = new MemManager(inMemMan);
        songTable = new Hash(inHash, manager);
        artistTable = new Hash(inHash, manager);
        memsize = inMemMan;
        size = inHash;
        return "";
    }


    /**
     * Re-initialize the database
     * 
     * @return true on successful clear of database
     */
    public boolean clear() {
        if (songTable == null) {
            return false;
        }
        manager = new MemManager(memsize);
        songTable = new Hash(size, manager);
        artistTable = new Hash(size, manager);
        return true;
    }
    // ----------------------------------------------------------
    /**
     * Insert to the hash table
     *
     * @param artistString
     *            Artist string to insert
     * @param songString
     *            Song string to insert
     * @return Error message if appropriate
     * @throws IOException
     */
    public String insert(String artistString, String songString)
        throws IOException {
        if (artistString == null || artistString == "" || songString == null
            || songString == "") {
            return "Input strings cannot be null or empty";
        }
        if (songTable == null) {
            return "Database not initialized";
        }

        // check for duplicates here
        // if necessary, call resize method and REHASH
        
        Handle songHand = manager.insert(songString);
        Handle artistHand = manager.insert(artistString);
        String songStr = songTable.insert(songHand, songString);
        String artistStr = artistTable.insert(artistHand, artistString);
          
        if (songStr != null || artistStr != null) {
         return "one or both strings weren't inserted";
        }
         
        return null;
    }


    // ----------------------------------------------------------
    /**
     * Remove from the hash table
     *
     * @param type
     *            The table to be removed
     * @param nameString
     *            The string to be removed from the table
     * @return Error message if appropriate
     * @throws IOException
     */
    public String remove(String type, String nameString) throws IOException {
        if (type == null || type == "" || nameString == null
            || nameString == "") {
            return "Input strings cannot be null or empty";
        }
        if (type != "song" && type != "artist") {
            return "Bad type value |" + type + "| on remove";
        }
        if (songTable == null) {
            return "Database not initialized";
        }
        if (type == "artist" && artistTable.find(nameString)==null) {
        	return "|"+nameString + "| does not exist in the artist database";
        }

        if (type == "song" && songTable.find(nameString)==null) {
        	return "|"+nameString + "| does not exist in the song database";
        }
        return "";
    }


    // ----------------------------------------------------------
    /*
     * Print out the hash table contents
     *
     * @param type
     *            Controls what object is being printed
     * @return The string that was printed
     * @throws IOException
     */
    public String print(String type) throws IOException {
        if (type == null || type == "") {
            return "Input strings cannot be null or empty";
        }
        if (songTable == null) {
            return "Database not initialized";
        }
        if (type.equals("blocks")) {
            return "No free blocks are available.";
        }
        if (!type.equals("song") && !type.equals("artist")) {
            return "Bad print parameter";
        }
        if(type.equals("artist") && artistTable.getSize() == 0) {
        	return "total artists: 0";
        }
        if(type.equals("song") && songTable.getSize() == 0) {
        	return "total songs: 0";
        }
        return "";
    }
    
}
