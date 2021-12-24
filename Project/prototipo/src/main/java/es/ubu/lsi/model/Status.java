package es.ubu.lsi.model;

import java.util.List;

public class Status {
    private int cmid;
    private String modname;
    private int instance;
    private int state;
    private int timecompleted;
    private int tracking;
    private Object overrideby;
    private boolean valueused;
    private boolean hascompletion;
    private boolean isautomatic;
    private boolean istrackeduser;
    private boolean uservisible;
    private List<Detail> details;

    public Status() {}

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

    public Object getOverrideby() {
        return overrideby;
    }

    public void setOverrideby(Object overrideby) {
        this.overrideby = overrideby;
    }

    public boolean isValueused() {
        return valueused;
    }

    public void setValueused(boolean valueused) {
        this.valueused = valueused;
    }

    public boolean isHascompletion() {
        return hascompletion;
    }

    public void setHascompletion(boolean hascompletion) {
        this.hascompletion = hascompletion;
    }

    public boolean isIsautomatic() {
        return isautomatic;
    }

    public void setIsautomatic(boolean isautomatic) {
        this.isautomatic = isautomatic;
    }

    public boolean isIstrackeduser() {
        return istrackeduser;
    }

    public void setIstrackeduser(boolean istrackeduser) {
        this.istrackeduser = istrackeduser;
    }

    public boolean isUservisible() {
        return uservisible;
    }

    public void setUservisible(boolean uservisible) {
        this.uservisible = uservisible;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }


}
