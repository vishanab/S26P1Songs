import java.io.IOException;

/**
 * Implement a hash table.
 * Data: Strings
 * Hash function: sfold
 * Collision Resolution: Quadratic probing
 *
 * @author Vishana Baskaran and Sital Paudel
 * @version 1/24/26
 */

public class Hash {

    private Handle[] table;
    private int capacity;
    private int size;

    /**
     * Create a new Hash object.
     *
     * @param init
     *            Initial size for table
     * @param m
     *            Memory manager used by this table to store objects
     */
    public Hash(int init, MemManager m) {
        table = new Handle[init];
        capacity = init;
        size = 0;
    }


    /**
     * Compute the hash function. Uses the "sfold" method from the OpenDSA
     * module on hash functions
     *
     * @param s
     *            The string that we are hashing
     * @param m
     *            The size of the hash table
     * @return The home slot for that string
     */
    public int h(String s, int m) {
        long sum = 0;
        long mult = 1;
        for (int i = 0; i < s.length(); i++) {
            mult = (i % 4 == 0) ? 1 : mult * 256;
            sum += s.charAt(i) * mult;
        }
        return (int)(Math.abs(sum) % m);
    }


    public int quadProbe(String s) {
        int home = h(s, capacity);
        int slot = home;
        int i = 0;
        while (table[slot] != null) {
            slot = (home + i * i) % capacity;
            i++;

        }
        return slot;

    }


    /**
     * Insert to the hash table
     *
     * @param hand
     *            handle to insert
     * @return Error message if appropriate
     * @throws IOException
     */
    public String insert(Handle hand) {
        size++;
        return "";

    }


    // ----------------------------------------------------------
    /**
     * Remove from the hash table
     * 
     * @param nameString
     *            The string to be removed from the table
     * @return Error message if appropriate
     * @throws IOException
     */
    public String remove(String nameString) throws IOException {
        size--;
        return "";
    }
    
    //resize if over 50% FULL
    public void resize() {
        Handle[] old = table;
        capacity = capacity * 2;
        table = new Handle[capacity];
        size = 0;
        for(Handle hand:old) {
            if(hand != null) {
                insert(hand);
            }
        }
        
        
    }
}
