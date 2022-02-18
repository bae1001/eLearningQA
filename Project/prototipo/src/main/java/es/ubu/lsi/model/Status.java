package es.ubu.lsi.model;

import java.util.List;

public class Status extends SuperClass{
    private int cmid;
    private String modname;
    private int instance;
    private int state;
    private int timecompleted;
    private int tracking;


    public int getCmid() {
        return cmid;
    }

    public void setCmid(int cmid) {
        this.cmid = cmid;
    }

    public String getModname() {
        return modname;
    }

    public void setModname(String modname) {
        this.modname = modname;
    }

    public int getInstance() {
        return instance;
    }

    public void setInstance(int instance) {
        this.instance = instance;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getTimecompleted() {
        return timecompleted;
    }

    public void setTimecompleted(int timecompleted) {
        this.timecompleted = timecompleted;
    }

    public int getTracking() {
        return tracking;
    }

    public void setTracking(int tracking) {
        this.tracking = tracking;
    }
}
