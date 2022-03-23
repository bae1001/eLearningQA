package es.ubu.lsi.model;

public class Completiondata extends MoodleObject {
    private long state;
    private long timecompleted;


    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }

    public long getTimecompleted() {
        return timecompleted;
    }

    public void setTimecompleted(long timecompleted) {
        this.timecompleted = timecompleted;
    }

}
