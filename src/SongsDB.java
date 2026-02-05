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
        String toRet = "";
        int artSize = artistTable.getCapacity();
        int songSize = songTable.getCapacity();
<<<<<<< HEAD
        Handle artistHand = artistTable.find(artistString);
        if (artistHand == null) {
            artistHand = manager.insert(artistString);
            if(manager.getResize()) {
                toRet+="Memory pool expanded to be " + manager.getMemSize() + " bytes\r\n";
            }
            String artistStr = artistTable.insert(artistHand, artistString);
            if (artistTable.getCapacity() > artSize) {
                toRet+= "Artist hash table size doubled\r\n";
            }
            if(artistStr==null) {
                toRet += artistString + " is added to the Artist database \r\n";
            }
        } else {
            toRet += artistString + " duplicates a record already in the Artist Database\r\n";
        }
        Handle songHand = songTable.find(songString);
        if (songHand == null) {
            songHand = manager.insert(songString);
            if(manager.getResize()) {
                toRet+="Memory pool expanded to be " + manager.getMemSize() + " bytes\r\n";
            }
            String songStr = songTable.insert(songHand, songString);
            if (songTable.getCapacity() > songSize) {
                toRet+= "Song hash table size doubled\r\n";
            }
            if(songStr==null) {
                toRet += songString + " is added to the Song database";
            }
        } else {
            toRet += artistString + " duplicates a record already in the Song Database";
=======
        Handle artistHand = manager.insert(artistString);
        if(manager.getResize()) {
            toRet+="Memory pool expanded to be " + manager.getMemSize() + " bytes\r\n";
        }
        String artistStr = artistTable.insert(artistHand, artistString);
        if (artistTable.getCapacity() > artSize) {
        	toRet+= "Artist hash table size doubled\r\n";
        }
        if(artistStr==null) {
            toRet += artistString + " is added to the Artist database \r\n";
        }
        Handle songHand = manager.insert(songString);
        if(manager.getResize()) {
            toRet+="Memory pool expanded to be " + manager.getMemSize() + " bytes\r\n";
        }
        String songStr = songTable.insert(songHand, songString);
        if (songTable.getCapacity() > songSize) {
        	toRet+= "Song hash table size doubled\r\n";
        }
        if(songStr==null) {
            toRet += songString + " is added to the Song database";
        }
        if (songStr != null || artistStr != null) {
         return "one or both strings weren't inserted";
>>>>>>> 124f055ec2018afdc2cc1ec978480205b0688317
        }

//        if (songStr != null || artistStr != null) {
//         return "one or both strings weren't inserted";
//        }



        return toRet;
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
        if (type.equals("artist")) {
         if (artistTable.find(nameString)==null) {
             return "|"+nameString + "| does not exist in the artist database";
         }
         artistTable.remove(nameString);
        }
        if (type.equals("song")) {
         if (songTable.find(nameString)==null) {
             return "|"+nameString + "| does not exist in the song database";
         }
         songTable.remove(nameString);
        }
       
     return "|"+nameString + "| is removed from the " + type + " database";
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
        if (!type.equals("song") && !type.equals("artist") && !type.equals("blocks")) {
            return "Bad print parameter";
        }
        if (type.equals("blocks")) {
            String toRet = manager.print();
            if (toRet.equals("")) {
                return "No free blocks are available.";
            }
            return toRet;
        }
        if(type.equals("artist")) {
         if (artistTable.getSize() == 0) {
             return "total artists: 0";
         }
         return artistTable.print(type);
        }
        if(type.equals("song")) {
         if (songTable.getSize() == 0) {
             return "total songs: 0";
         }
         return songTable.print(type);
        }
        if (type.equals("block")) {
            return manager.print();
        }
        return "";
    }

    
}
