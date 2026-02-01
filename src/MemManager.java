
/**
 * Memory Manager class.
 * This version uses an array in memory.
 * This version implements the buddy method.
 *
 * @author Vishana Baskaran and Sital Paudel
 * @version 1/24/26
 */

public class MemManager {
     /**
     * Create a new MemManager object.
     *
     * @param startSize
     *            Initial size of the memory pool
     */
    int startSize = 0;
    public MemManager(int startSize) {
        this.startSize = startSize;
    }
    public buddyMethod(int size) {
        int blockSize = 2;
        while (blockSize < size) {
            blockSize *= 2;
        }
        
    }
    
}
