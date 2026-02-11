/**
 * Implement a handle table.
 * 
 * @author Vishana Baskaran and Sital Paudel
 * @version 2/1/26
 */

public class Handle {
    private int index;
    private int size;

    /**
     * Creates a new Handle object
     *
     * @param i
     *            Starting index in the memory pool
     * @param s
     *            Size of the record
     */
    public Handle(int i, int s) {
        index = i;
        size = s;
    }


    /**
     * returns the index of handle
     *
     * @return index location in the memory pool
     */
    public int getIndex() {
        return index;
    }


    /**
     * returns the size of handle
     *
     * @return size of the handle
     */
    public int getSize() {
        return size;
    }
}
