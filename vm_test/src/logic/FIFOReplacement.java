package logic;

import model.PageTable;

import java.util.LinkedList;
import java.util.Queue;

public class FIFOReplacement extends PRA {
    private Queue<Integer> pageQueue;
    public FIFOReplacement(){
        pageQueue = new LinkedList<>();
    }
    @Override
    public int replace(PageTable pageTable, int newPageNumber){
        int pageToReplace = pageQueue.poll();
        pageTable.removePage(pageToReplace);
        pageQueue.add(newPageNumber);
        return pageToReplace;
    }
    @Override
    public void addPage(int pageNumber){
        pageQueue.add(pageNumber);
    }
}
