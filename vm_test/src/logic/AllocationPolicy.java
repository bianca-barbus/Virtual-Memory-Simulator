package logic;

import java.util.List;

public abstract class AllocationPolicy {
    public abstract int[] chooseBlock(int segmentSize, List<int[]> freeBlocks);
}
