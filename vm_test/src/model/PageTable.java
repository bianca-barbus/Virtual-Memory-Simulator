package model;

import java.util.HashMap;

public class PageTable {
    private HashMap<Integer, Page> pages;
    private int maxSize;
    public PageTable(int maxSize){
        this.maxSize = maxSize;
        this.pages = new HashMap<>();
    }
    public boolean containsPage(int pageNumber){
        return pages.containsKey(pageNumber);
    }
    public void addPage(Page page){
        pages.put(page.getPageNumber(), page);
    }
    public void removePage(int pageNumber){
        pages.remove(pageNumber);
    }
    public Page getPage(int pageNumber){
        return pages.get(pageNumber);
    }
    public HashMap<Integer, Page> getPages(){
        return pages;
    }
    public int size(){
        return pages.size();
    }
    public boolean isFull(){
        return pages.size() >= maxSize;
    }
}
