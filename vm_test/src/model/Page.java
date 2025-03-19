package model;

public class Page {
    private int pageNumber;
    private long lastAccessed;
    private String data;
    public Page(int pageNumber, String data){
        this.pageNumber = pageNumber;
        this.data = data;
        updateAccessTime();
    }
    public Page(int pageNumber){
        this.pageNumber = pageNumber;
        this.data = null;
        updateAccessTime();
    }
    public int getPageNumber() {
        return pageNumber;
    }
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public long getLastAccessed() {
        return lastAccessed;
    }
    public void updateAccessTime(){
        this.lastAccessed = System.nanoTime();
    }
    @Override
    public String toString() {
        return "Page " + pageNumber + (data != null ? " [Data: " + data + "]" : " [No Data]");
    }
}
