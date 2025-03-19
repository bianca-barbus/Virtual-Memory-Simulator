package logic;

import java.util.List;

public class FirstFitPolicy extends AllocationPolicy {
    @Override
    public int[] chooseBlock(int segmentSize, List<int[]> freeBlocks) {
        return findFirstFitBlock(segmentSize, freeBlocks);
    }

    private int[] findFirstFitBlock(int segmentSize, List<int[]> freeBlocks) {
        for (int[] block : freeBlocks) {
            if (block[1] >= segmentSize) {
                return block;
            }
        }
        return new int[]{-1, -1};
    }
}
