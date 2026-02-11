
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
    private boolean resize;

    public MemManager(int startSize) {
        memory = new byte[startSize];
        int blockSize = 1;
        k = 0;
        while (blockSize < startSize) {
            blockSize *= 2;
            k += 1;
        }
        offsets = new int[k + 1][startSize];
        count = new int[k + 1];
        offsets[k][0] = 0;
        count[k] = 1;
        resize = false;
    }


    public Handle insert(String record) {
        byte[] storage = record.getBytes();
        int size = storage.length;
        int index = buddyMethod(size);
        int i = index;
        while (i <= k && count[i] == 0) {
            i++;
        }
        if (i > k) {
            resize();
            return insert(record);
        }
        while (i > index) {
            int off = offsets[i][count[i] - 1];
            count[i] = count[i] - 1;
            int half = 1;
            for (int x = 0; x < i - 1; x++) {
                half = half * 2;
            }
            int p = count[i - 1];
            offsets[i - 1][p] = off;
            offsets[i - 1][p + 1] = off + half;
            count[i - 1] = count[i - 1] + 2;
            i = i - 1;
        }
        int off = offsets[index][count[index] - 1];
        count[index] = count[index] - 1;
        for (int h = 0; h < size; h++) {
            memory[off + h] = storage[h];
        }
        return new Handle(off, size);
    }


    public String find(Handle h) {
        int index = h.getIndex();
        int size = h.getSize();
        String res = "";
        for (int i = 0; i < size; i++) {
            res += (char)memory[index + i];
        }
        return res;
    }


    public int buddyMethod(int size) {
        int blockSize = 1;
        int j = 0;
        while (blockSize < size) {
            blockSize *= 2;
            j += 1;
        }
        return j;
    }


    public void resize() {
        resize = true;
        int nSize = memory.length * 2;
        byte[] nMem = new byte[nSize];
        for (int i = 0; i < memory.length; i++) {
            nMem[i] = memory[i];
        }
        k = k + 1;
        int[][] nOffsets = new int[k + 1][nSize];
        int[] nCount = new int[k + 1];
        for (int j = 0; j < offsets.length; j++) {
            for (int h = 0; h < count[j]; h++) {
                nOffsets[j][h] = offsets[j][h];
            }
            nCount[j] = count[j];
        }
        nOffsets[k - 1][nCount[k - 1]] = memory.length;
        nCount[k - 1] += 1;

        offsets = nOffsets;
        count = nCount;
        memory = nMem;
    }


    // insert method
    // find method if argument is handle, return string
    public boolean getResize() {
        boolean toRet = resize;
        resize = false;
        return toRet;
    }


    public int getMemSize() {
        return memory.length;
    }


    public String print() {
        String toRet = "";
        
        for (int i = 0; i <= k; i++) {
            if (count[i] > 0) {
                int block = 1;
                for (int j = 0; j < i; j++) {
                    block *= 2;
                }
                if (i != 0) {
                    toRet += "\r\n";
                }
                toRet += block;
                for (int j = 0; j < count[i] && j < offsets[i].length; j++) {
                    toRet += " " + offsets[i][j];
                }

                if (i < k - 1) {
                    toRet += "\r\n";
                }
            }
        }
        return toRet;
    }
    
    public void remove(Handle h) {
        int off = h.getIndex();
        int size = h.getSize();
        int level = buddyMethod(size);
        
        offsets[level][count[level]] = off;
        count[level] += 1;
        
        merge(level);
    }
    public void merge(int level) {
        if (level >= k) {
            return;
        }
        int blockSize = 1;
        for (int i = 0; i < level; i++) {
            blockSize *= 2;
        }
        for (int i = 0; i < count[level] - 1; i++) {
            for (int j = 0; j < count[level]; j++) {
                int off1 = offsets[level][i];
                int off2 = offsets[level][j];
                int dif = off1 > off2 ? off1 - off2 : off2 - off1;
                
                if (dif == blockSize) {
                    int l = off1 < off2 ? off1 : off2;
                    if (l % (blockSize * 2) == 0) {
                        removeOff(level, i);
                        removeOff(level, j > 1 ? j - 1 : j);
                        offsets[level + 1][count[level + 1]++] = l;
                        merge(level + 1);
                        return;
                    }
                }
            }
        }
    }
    
    public void removeOff(int level, int index) {
        for (int i = index; i < count[level] - 1; i++) {
            offsets[level][i] = offsets[level][i + 1];
        }
        count[level] -= 1;
    }

    
}
