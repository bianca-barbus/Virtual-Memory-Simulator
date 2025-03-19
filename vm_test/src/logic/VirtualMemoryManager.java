package logic;

import model.Page;
import model.PageTable;
import model.Segment;

import java.util.ArrayList;
import java.util.List;

public class VirtualMemoryManager {
    private PageTable pageTable;
    private SegmentManager segmentManager;
    private MainMemoryManager mainMemoryManager;
    private List<int[]> freeBlocks = new ArrayList<>();
    private boolean isSegmented;
    private int totalRequests;
    private int hits;
    private int misses;
    private List<String> accessLog;

    // Constructor for paging
    public VirtualMemoryManager(int memorySize, PRA pageReplacementAlgorithm) {
        this.pageTable = new PageTable(memorySize);
        this.mainMemoryManager = new MainMemoryManager(pageTable, pageReplacementAlgorithm);
        this.isSegmented = false;
        this.totalRequests = 0;
        this.hits = 0;
        this.misses = 0;
        this.accessLog = new ArrayList<>();
    }

    // Constructor for segmentation
    public VirtualMemoryManager(List<int[]> freeBlocks, AllocationPolicy allocationPolicy) {
        if (freeBlocks != null) {
            this.segmentManager = new SegmentManager(freeBlocks, allocationPolicy);
        } else {
            this.segmentManager = new SegmentManager(this.freeBlocks, allocationPolicy);
        }
        this.isSegmented = true;
    }

    public void requestPage(int pageNumber) {
        requestPage(pageNumber, null);
    }

    public void requestPage(int pageNumber, String data) {
        totalRequests++;
        if (isSegmented) {
            System.out.println("Segmentation is enabled. Use requestSegment instead.");
            return;
        }

        if (pageTable.containsPage(pageNumber)) {
            hits++;
            accessLog.add("Page " + pageNumber + " accessed\n");
            handlePageHit(pageNumber, data);
        } else {
            misses++;
            handlePageFault(pageNumber, data);
        }
    }

    private void handlePageHit(int pageNumber, String data) {
        Page page = pageTable.getPage(pageNumber);
        System.out.println("Page " + pageNumber + " accessed (already in memory).");
        if (data != null) {
            page.setData(data);
            System.out.println("Page " + pageNumber + " data updated to: " + data);
        }
        page.updateAccessTime();
    }

    private void handlePageFault(int pageNumber, String data) {
        System.out.println("Page fault! Page " + pageNumber + " not found in memory.");
        mainMemoryManager.handlePageFault(pageNumber, data);
    }

    public void requestSegment(int size, String data) {
        if (!isSegmented) {
            System.out.println("Paging is enabled. Use requestPage instead.");
            return;
        }

        Segment segment = new Segment(size);
        segment.setData(data);
        segmentManager.allocateSegment(segment, segmentManager.getAllocationPolicy(), segmentManager.getFreeBlocks());
    }

    public void addFreeBlock(int baseAddress, int size) {
        freeBlocks.add(new int[]{baseAddress, size});
    }

    public String displayMemoryState() {
        StringBuilder memoryInfo = new StringBuilder();

        if (isSegmented) {
            memoryInfo.append("===== Current Segments in Memory =====\n");
            for (Segment segment : segmentManager.getAllocatedSegments()) {
                memoryInfo.append("Segment ID: ").append(segment.hashCode())
                        .append("\n  Base Address: ").append(segment.getBaseAddress())
                        .append("\n  Limit: ").append(segment.getLimit())
                        .append("\n  Data: ").append(segment.getData() != null ? segment.getData() : "No Data")
                        .append("\n--------------------------------------\n");
            }

            memoryInfo.append("\n===== Free Blocks =====\n");
            for (int[] block : segmentManager.getFreeBlocks()) {
                memoryInfo.append("Block:\n  Base Address: ").append(block[0])
                        .append("\n  Size: ").append(block[1])
                        .append("\n--------------------------------------\n");
            }
        } else {
            memoryInfo.append("===== Current Pages in Memory =====\n");
            for (Page page : pageTable.getPages().values()) {
                memoryInfo.append("Page Number: ").append(page.getPageNumber())
                        .append("\n  Data: ").append(page.getData())
                        .append("\n--------------------------------------\n");
            }
            if (!accessLog.isEmpty()) {
                memoryInfo.append("\nAccess Log:\n");
                for (String logEntry : accessLog) {
                    memoryInfo.append(logEntry).append("\n");
                }
            }
            memoryInfo.append("===== Statistics =====\n")
                    .append("  Hit Rate: ").append(String.format("%.2f", (double) hits / totalRequests * 100)).append(" %\n")
                    .append("  Miss Rate: ").append(String.format("%.2f", (double) misses / totalRequests * 100)).append(" %\n");
        }
        memoryInfo.append("---------------------------------------------------------------------------------------------" +
                "-----------------------------------------------------------------------\n\n");

        return memoryInfo.toString();
    }

}
