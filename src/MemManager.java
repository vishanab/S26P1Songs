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
        offsets = new int[k + 1][];
        for (int i = 0; i <= k; i++) {
            offsets[i] = new int[startSize >> i];
        }
        count = new int[k + 1];
        offsets[k][0] = 0;
        count[k] = 1;
        resize = false;
    }


    /**
     * Inserts a string record into memory using the buddy method.
     *
     * @param record
     *            The string to store
     * @return Handle containing the starting index and size of the stored
     *         record
     */
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
            int off = offsets[i][0];
            for (int j = 0; j < count[i] - 1; j++) {
                offsets[i][j] = offsets[i][j + 1];
            }
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
        int off = offsets[index][0];
        for (int j = 0; j < count[index] - 1; j++) {
            offsets[index][j] = offsets[index][j + 1];
        }
        count[index] = count[index] - 1;
        for (int h = 0; h < size; h++) {
            memory[off + h] = storage[h];
        }
        return new Handle(off, size);
    }


    /**
     * Finds a record in memory given its handle.
     *
     * @param h
     *            Handle pointing to the memory location
     * @return The string stored at the handle's location
     */
    public String find(Handle h) {
        int index = h.getIndex();
        int size = h.getSize();
        String res = "";
        for (int i = 0; i < size; i++) {
            res += (char)memory[index + i];
        }
        return res;
    }


    /**
     * Computes the buddy system level needed to store a block of a given size.
     *
     * @param size
     *            The size of the block to store
     * @return The level in the buddy system
     */
    public int buddyMethod(int size) {
        int blockSize = memory.length;
        int j = k;
        while (blockSize / 2 >= size) {
            blockSize /= 2;
            j -= 1;
        }
        return j;
    }


    /**
     * Resizes the memory pool to double its current size.
     */
    public void resize() {
        resize = true;
        int oldSize = memory.length;
        int nSize = memory.length * 2;
        byte[] nMem = new byte[nSize];
        for (int i = 0; i < memory.length; i++) {
            nMem[i] = memory[i];
        }
        int oldK = k;
        k = k + 1;
        int[][] nOffsets = new int[k + 1][];
        for (int i = 0; i <= k; i++) {
            int blocks = nSize;
            for (int j = 0; j < i; j++) {
                blocks /= 2;
            }
            nOffsets[i] = new int[blocks];
        }
        int[] nCount = new int[k + 1];
        for (int j = 0; j < offsets.length; j++) {
            for (int h = 0; h < count[j]; h++) {
                nOffsets[j][h] = offsets[j][h];
            }
            nCount[j] = count[j];
        }
        nOffsets[k - 1][nCount[k - 1]] = oldSize;
        nCount[k - 1] += 1;

        offsets = nOffsets;
        count = nCount;
        memory = nMem;
    }


    /**
     * Returns and resets the resize flag.
     *
     * @return True if the memory was resized since the last check
     */
    public boolean getResize() {
        boolean toRet = resize;
        resize = false;
        return toRet;
    }


    /**
     * Returns the current size of the memory pool.
     *
     * @return The size of the memory pool
     */
    public int getMemSize() {
        return memory.length;
    }


    /**
     * Prints the memory block offsets for each level.
     *
     * @return A string representation of memory offsets
     */
    public String print() {
        String toRet = "";
        boolean first = true;
        for (int i = 0; i <= k; i++) {
            if (count[i] > 0) {
                int block = 1;
                for (int j = 0; j < i; j++) {
                    block *= 2;
                }
                int n = count[i];
                int[] arr = offsets[i];
                for (int j = 0; j < n - 1; j++) {
                    for (int x = j + 1; x < n; x++) {
                        if (arr[j] > arr[x]) {
                            int tmp = arr[j];
                            arr[j] = arr[x];
                            arr[x] = tmp;
                        }
                    }
                }
                if (!first) {
                    toRet += "\r\n";
                }
                first = false;
                toRet += block;
                for (int j = 0; j < count[i] && j < offsets[i].length; j++) {
                    toRet += " " + offsets[i][j];
                }
            }
        }
        return toRet;
    }


    /**
     * Removes a block from memory, returning it to the free list and attempting
     * to merge buddies.
     *
     * @param h
     *            The handle pointing to the block to remove
     */
    public void remove(Handle h) {
        int off = h.getIndex();
        int size = h.getSize();
        int level = buddyMethod(size);
        while (level < k) {
            int block = 1;
            for (int i = 0; i < level; i++) {
                block *= 2;
            }
            int buddy = off ^ block;
            int buddyInd = findMerge(level, buddy);
            if (buddyInd == -1) {
                break;
            }
            removeOff(level, buddyInd);
            int mergedSize = block * 2;
            off = (off / mergedSize) * mergedSize;
            level++;
        }
        offsets[level][count[level]] = off;
        count[level]++;
    }


    /**
     * checks if there is a merge opp.
     *
     * @param level
     *            The buddy system level
     * @param buddyOff
     *            the offset
     * @return integer for the merge
     */
    private int findMerge(int level, int buddyOff) {
        for (int i = 0; i < count[level]; i++) {
            if (offsets[level][i] == buddyOff) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Removes an offset from a specific level's offset array.
     *
     * @param level
     *            The buddy system level
     * @param index
     *            The index of the offset to remove
     */
    private void removeOff(int level, int index) {
        for (int i = index; i < count[level] - 1; i++) {
            offsets[level][i] = offsets[level][i + 1];
        }
        count[level] -= 1;
    }
}
