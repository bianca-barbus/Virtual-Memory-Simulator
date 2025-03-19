package logic;

import logic.PRA;
import model.Page;
import model.PageTable;

import java.util.Map;

public class LRUReplacement extends PRA {

    @Override
    public int replace(PageTable pageTable, int newPageNumber) {
        long oldestAccessTime = Long.MAX_VALUE;
        int pageToReplace = -1;

        for (Map.Entry<Integer, Page> entry : pageTable.getPages().entrySet()) {
            Page page = entry.getValue();
            if (page.getLastAccessed() < oldestAccessTime) {
                oldestAccessTime = page.getLastAccessed();
                pageToReplace = page.getPageNumber();
            }
        }

        pageTable.removePage(pageToReplace);
        return pageToReplace;
    }
}
