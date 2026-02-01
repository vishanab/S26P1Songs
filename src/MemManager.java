
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
    int[] memory;
    public MemManager(int startSize) {
        memory = new int[startSize];
    }
    
    public int[] buddyMethod(int size) {
        int blockSize = 2;
        while (blockSize < size) {
            blockSize *= 2;
        }
        memory[blockSize]
    }
    
}
