package logic;

import model.Segment;
import java.util.*;

public class SegmentManager {
    private int maxSegments = 5;
    private List<int[]> freeBlocks;
    private Queue<Segment> allocatedSegments;
    private AllocationPolicy allocationPolicy;

    public SegmentManager(List<int[]> freeBlocks, AllocationPolicy allocationPolicy) {
        this.freeBlocks = freeBlocks;
        this.allocatedSegments = new LinkedList<>();
        this.allocationPolicy = allocationPolicy;
    }


    public void allocateSegment(Segment segment, AllocationPolicy policy, List<int[]> freeBlocks) {
        int[] chosenBlock = policy.chooseBlock(segment.getLimit(), freeBlocks);

        if (chosenBlock[0] != -1) {
            segment.setBaseAddress(chosenBlock[0]);
            freeBlocks.remove(chosenBlock);
            allocatedSegments.add(segment);
            System.out.println("Allocated Segment: " + segment + " to Block: [" + chosenBlock[0] + ", " + chosenBlock[1] + "]");
        } else {
            System.out.println("No suitable block found for segment with size " + segment.getLimit() + " units.");

            if (allocatedSegments.size() > maxSegments) {
                // Evict the oldest segment (FIFO)
                Segment evictedSegment = allocatedSegments.poll();
                System.out.println("Evicted segment: " + evictedSegment);
                // Add the evicted block back to the free blocks queue
                freeBlocks.add(new int[]{evictedSegment.getBaseAddress(), evictedSegment.getLimit()});

                chosenBlock = policy.chooseBlock(segment.getLimit(), freeBlocks);
                if (chosenBlock[0] != -1) {
                    segment.setBaseAddress(chosenBlock[0]);
                    freeBlocks.remove(chosenBlock);
                    allocatedSegments.add(segment);
                    System.out.println("Allocated Segment after eviction: " + segment + " to Block: [" + chosenBlock[0] + ", " + chosenBlock[1] + "]");
                } else {
                    System.out.println("Failed to allocate the segment even after eviction.");
                }
            } else {
                System.out.println("Failed to allocate segment: No suitable block found.");
            }
        }
    }

    public List<Segment> getAllocatedSegments() {
        return new ArrayList<>(allocatedSegments);
    }

    public List<int[]> getFreeBlocks() {
        return freeBlocks;
    }


    public AllocationPolicy getAllocationPolicy() {
        return allocationPolicy;
    }
}
