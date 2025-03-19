package logic;

import java.util.*;

public class MemorySimulator {

    public String runSimulation() {
        StringBuilder output = new StringBuilder();

        // FIFO Replacement Policy for pages
        output.append("FIFO Replacement for Pages:\n");
        VirtualMemoryManager virtualMemoryFIFO = new VirtualMemoryManager(4, new FIFOReplacement());
        virtualMemoryFIFO.requestPage(1, "Page 1");
        virtualMemoryFIFO.requestPage(2, "Page 2");
        virtualMemoryFIFO.requestPage(3, "Page 3");
        virtualMemoryFIFO.requestPage(4, "Page 4");
        virtualMemoryFIFO.requestPage(5, "Page 5");
        virtualMemoryFIFO.requestPage(1);
        virtualMemoryFIFO.requestPage(6, "Page 6");
        virtualMemoryFIFO.requestPage(2);
        output.append(virtualMemoryFIFO.displayMemoryState()).append("\n");

        // LRU Replacement Policy for pages
        output.append("\nLRU Replacement for Pages:\n");
        VirtualMemoryManager virtualMemoryLRU = new VirtualMemoryManager(4, new LRUReplacement());
        virtualMemoryLRU.requestPage(1, "Page 1");
        virtualMemoryLRU.requestPage(2, "Page 2");
        virtualMemoryLRU.requestPage(3, "Page 3");
        virtualMemoryLRU.requestPage(4, "Page 4");
        virtualMemoryLRU.requestPage(1);
        virtualMemoryLRU.requestPage(5, "Page 5");
        virtualMemoryLRU.requestPage(6, "Page 6");
        virtualMemoryLRU.requestPage(7, "Page 7");
        virtualMemoryLRU.requestPage(3);
        output.append(virtualMemoryLRU.displayMemoryState());

        // Segment allocation
        output.append(runSegmentAllocation(new FirstFitPolicy())).append("\n");
        output.append(runSegmentAllocation(new BestFitPolicy())).append("\n");

        return output.toString();
    }

    private String runSegmentAllocation(AllocationPolicy allocationPolicy) {
        StringBuilder output = new StringBuilder();
        output.append("Testing Segment Allocation with ").append(allocationPolicy.getClass().getSimpleName()).append(":\n");

        List<int[]> freeBlocks = new ArrayList<>();
        freeBlocks.add(new int[]{0, 50});
        freeBlocks.add(new int[]{60, 30});
        freeBlocks.add(new int[]{100, 60});
        freeBlocks.add(new int[]{170, 40});

        output.append("Initial Free Blocks:\n");
        for (int[] block : freeBlocks) {
            output.append("Block: [Base Address: ").append(block[0])
                    .append(", Size: ").append(block[1])
                    .append("]\n");
        }

        VirtualMemoryManager virtualMemory = new VirtualMemoryManager(freeBlocks, allocationPolicy);
        virtualMemory.requestSegment(25, "Segment 1 Data");
        virtualMemory.requestSegment(40, "Segment 2 Data");
        virtualMemory.requestSegment(15, "Segment 3 Data");
        virtualMemory.requestSegment(20, "Segment 4 Data");
        virtualMemory.requestSegment(10, "Segment 5 Data");
        virtualMemory.requestSegment(30, "Segment 6 Data");
        output.append(virtualMemory.displayMemoryState());

        return output.toString();
    }
}
