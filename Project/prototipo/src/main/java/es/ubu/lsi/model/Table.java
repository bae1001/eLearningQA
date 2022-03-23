package es.ubu.lsi.model;

import java.util.List;

public class Table{
    private long courseid;
    private long userid;
    private String userfullname;
    private int maxdepth;
    private List<Tabledata> tabledata;


    public long getCourseid() {
        return courseid;
    }

    public void setCourseid(long courseid) {
        this.courseid = courseid;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
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
