package es.ubu.lsi.model;

public class Response{
    private int id;
    private String name;
    private String printval;
    private String rawval;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrintval() {
        return printval;
    }

    public void setPrintval(String printval) {
        this.printval = printval;
    }

    public String getRawval() {
        return rawval;
    }

    public void setRawval(String rawval) {
        this.rawval = rawval;
    }
}
