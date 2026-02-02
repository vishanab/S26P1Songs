
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
    private int k;
    private byte[] memory;
    private int[][] offsets;
    private int[] count;
    
    public MemManager(int startSize) {
        memory = new byte[startSize];
        int blockSize = 1;
        k = 0;
        while (blockSize < startSize) {
            blockSize *= 2;
            k+=1;
        }
        offsets = new int[k+1][startSize];
        count = new int[k+1];
        offsets[k][0] = 0;
        count[k] = 1;
    }
    
    public Handle insert(String record) {
        
        byte[] storage = record.getBytes();
        int size = storage.length;
        int index = buddyMethod(size);
        
        
        
    }
    
    public int buddyMethod(int size) {
        int blockSize = 1;
        int k = 0;
        while (blockSize < size) {
            blockSize *= 2;
            k+=1;
        }
        return k;
    }
    
    
    //insert method
    //find method if argument is handle, return string
    
}
