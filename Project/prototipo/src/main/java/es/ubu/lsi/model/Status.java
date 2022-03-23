package es.ubu.lsi.model;

public class Status extends MoodleObject {
    private long cmid;
    private String modname;
    private int instance;
    private int state;
    private long timecompleted;
    private int tracking;


    public long getCmid() {
        return cmid;
    }

    public void setCmid(long cmid) {
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

    public long getTimecompleted() {
        return timecompleted;
    }

    public void setTimecompleted(long timecompleted) {
        this.timecompleted = timecompleted;
    }

    public int getTracking() {
        return tracking;
    }

    public void setTracking(int tracking) {
        this.tracking = tracking;
    }
}
