/**
 * Implement a handle table.
 * 
 * @author Vishana Baskaran and Sital Paudel
 * @version 2/1/26
 */

public class Handle {
    private int index;
    private int size;

    public Handle(int i, int s) {
        index = i;
        size = s;
    }


    public int getIndex() {
        return index;
    }


    public int getSize() {
        return size;
    }
}
