package es.ubu.lsi.model;

public class Completiondata extends MoodleObject {
    private int state;
    private int timecompleted;


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

}
