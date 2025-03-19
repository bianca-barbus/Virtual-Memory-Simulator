package logic;

import logic.PRA;
import model.Page;
import model.PageTable;

public class MainMemoryManager {
    private PageTable pageTable;
    private PRA pageReplacementAlgorithm;

    public MainMemoryManager(PageTable pageTable, PRA pageReplacementAlgorithm) {
        this.pageTable = pageTable;
        this.pageReplacementAlgorithm = pageReplacementAlgorithm;
    }

    public void handlePageFault(int pageNumber, String data) {
        Page newPage;
        if (data != null) {
            newPage = new Page(pageNumber, data);
        } else {
            newPage = new Page(pageNumber);
        }

        if (pageTable.isFull()) {
            int replacedPageNumber = pageReplacementAlgorithm.replace(pageTable, pageNumber);
            System.out.println("Replacing page " + replacedPageNumber + " with page " + pageNumber + ".");
        }

        pageTable.addPage(newPage);
        pageReplacementAlgorithm.addPage(pageNumber);
    }
}
