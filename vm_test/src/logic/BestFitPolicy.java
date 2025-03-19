package logic;

import java.util.List;

public class BestFitPolicy extends AllocationPolicy {
    @Override
    public int[] chooseBlock(int segmentSize, List<int[]> freeBlocks) {
        return findBestFitBlock(segmentSize, freeBlocks);
    }

    private int[] findBestFitBlock(int segmentSize, List<int[]> freeBlocks) {
        int minBlockSize = Integer.MAX_VALUE;
        int[] bestBlock = new int[]{-1, -1};

        for (int[] block : freeBlocks) {
            if (block[1] >= segmentSize && block[1] < minBlockSize) {
                bestBlock = block;
                minBlockSize = block[1];
            }
        }
        return bestBlock;
    }
}
