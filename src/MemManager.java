
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
        record = "Trouble";
        byte[] storage = record.getBytes();
        int size = storage.length;
        int index = buddyMethod(size);
        int i = index;
        while (i<=k && count[i] == 0) {
            i++;
        }
        while (i > index) {
            int off = offsets[i][count[i]-1];
            count[i] = count[i]-1;
            int half = 1;
            for (int x = 0; x < i-1; x++) {
                half = half * 2;
            }
            offsets[i-1][count[i-1]] = off;
            count[i-1] = count[i-1] + 1;
            offsets[i-1][count[i-1]] = off + half;
            count[i-1] = count[i-1] + 1;
            i = i-1;
        }
        int off = offsets[index][count[index]-1];
        count[index] = count[index]-1;
        for(int k = 0; k < size; k++) {
            memory[off+1] = storage[k];
        }
        return new Handle(off, size);
    }
    
    public String find(Handle h) {
        int index = h.getIndex();
        int size = h.getSize();
        String res = "";
        for (int i = 0; i<size; i++) {
            res += "" + memory[index+i];
        }
        return res;
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
    
//    public void remove(Handle h) {
//        int size = h.getSize();
//        int index = buddyMethod(size);
//        int off = h.getIndex();
//        
//            
//        
//    }
    
    //insert method
    //find method if argument is handle, return string
    
}
