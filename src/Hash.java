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
    private MemManager manager;
    private static final Handle TOMB = new Handle(-1,0);

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
        manager = m;
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


    /**
     * Insert to the hash table
     *
     * @param hand
     *            handle to insert
     * @param s
     *            string to insert
     * @return Error message if appropriate
     * @throws IOException
     */
    public String insert(Handle hand, String s) {
    	if ((size + 1) > capacity/2) {
    		resize();
    	}
        int home = h(s, capacity);
        int slot = home;
        int i = 0;
        while (table[slot] != null) {
            slot = (home + i * i) % capacity;
            i++;

        }
    	table[slot] = hand;
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
    /*
    public String remove(String nameString) throws IOException {
        int home = h(nameString, capacity);
        int slot = home;
        int i = 0;
        while (table[slot] != null) {
        	String curr = manager.find(table[slot]);
        	if (curr.equals(nameString)) {
            	size--;
            	table[slot] = TOMB;
        	}
            slot = (home + i * i) % capacity;
            i++;

        }
        return "";
    }*/


    // resize if over 50% FULL
    public void resize() {
        Handle[] old = table;
        capacity = capacity * 2;
        table = new Handle[capacity];
        size = 0;
        for(Handle hand:old) {
         if(hand != null) {
        	 String s = manager.find(hand);
        	 insert(hand,s);
         }
        }

    }
    
    public Handle find(String s) {
        int home = h(s, capacity);
        int slot = home;
        int i = 0;
        while (table[slot] != null) {
        	String curr = manager.find(table[slot]);
        	if (curr.equals(s)) {
            	return table[slot];
        	}
            slot = (home + i * i) % capacity;
            i++;

        }
        return null;
    }
    public int getSize() {
    	return size;
    }
    public int getCapacity() {
    	return capacity;
    }
    
    public String print(String type) {
    	String output = "";
    	for(int i = 0; i < capacity; i++) {
    		if (table[i] != null) {
    			String name = manager.find(table[i]);
    			output+= i+": |" + name + "|\n";
    		}
    	}
    	output+= "total " + type + "s: " + size;
    	return output;
    }
}
