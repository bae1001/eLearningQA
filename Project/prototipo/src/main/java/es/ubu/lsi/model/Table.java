package es.ubu.lsi.model;

import java.util.List;

public class Table{
    private int courseid;
    private int userid;
    private String userfullname;
    private int maxdepth;
    private List<Tabledata> tabledata;

    public Table() {}


    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUserfullname() {
        return userfullname;
    }

    public void setUserfullname(String userfullname) {
        this.userfullname = userfullname;
    }

    public int getMaxdepth() {
        return maxdepth;
    }

    public void setMaxdepth(int maxdepth) {
        this.maxdepth = maxdepth;
    }

    public List<Tabledata> getTabledata() {
        return tabledata;
    }

    public void setTabledata(List<Tabledata> tabledata) {
        this.tabledata = tabledata;
    }
}
