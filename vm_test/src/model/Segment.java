package model;

public class Segment {
    private int baseAddress;
    private int limit;
    private String data;
    public Segment(int limit){
        this.baseAddress = -1;
        this.limit = limit;
        this.data = null;
    }
    public Segment(int limit, String data){
        this.baseAddress = -1;
        this.limit = limit;
        this.data = data;
    }
    public int getBaseAddress() {
        return baseAddress;
    }

    public void setBaseAddress(int baseAddress) {
        this.baseAddress = baseAddress;
    }

    public int getLimit() {
        return limit;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString(){
        return "[" + baseAddress + ", " + limit + "]";
    }
}
