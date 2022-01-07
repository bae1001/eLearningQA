package es.ubu.lsi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Itemname{
    @JsonProperty("class")
    private String myclass;
    private int colspan;
    private String content;
    private String celltype;
    private String id;

    public Itemname() {}


    public String getMyclass() {
        return myclass;
    }

    public void setMyclass(String myclass) {
        this.myclass = myclass;
    }

    public int getColspan() {
        return colspan;
    }

    public void setColspan(int colspan) {
        this.colspan = colspan;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCelltype() {
        return celltype;
    }

    public void setCelltype(String celltype) {
        this.celltype = celltype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
