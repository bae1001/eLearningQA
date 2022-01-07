package es.ubu.lsi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Leader{
    @JsonProperty("class")
    private String myclass;
    private int rowspan;

    public Leader() {}


    public String getMyclass() {
        return myclass;
    }

    public void setMyclass(String myclass) {
        this.myclass = myclass;
    }

    public int getRowspan() {
        return rowspan;
    }

    public void setRowspan(int rowspan) {
        this.rowspan = rowspan;
    }
}
