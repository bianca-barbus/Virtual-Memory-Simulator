package logic;

import model.PageTable;

public abstract class PRA {
    public abstract int replace(PageTable pageTable, int newPageNumber);
    public void addPage(int pageNumber){}
}
