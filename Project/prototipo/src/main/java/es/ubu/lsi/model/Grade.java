package es.ubu.lsi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Grade{
    private long id;
    private long userid;
    private int attemptnumber;
    private long timecreated;
    private long timemodified;
    private long grader;
    @JsonProperty("grade")
    private String gradeValue;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public int getAttemptnumber() {
        return attemptnumber;
    }

    public void setAttemptnumber(int attemptnumber) {
        this.attemptnumber = attemptnumber;
    }

    public long getTimecreated() {
        return timecreated;
    }

    public void setTimecreated(long timecreated) {
        this.timecreated = timecreated;
    }

    public long getTimemodified() {
        return timemodified;
    }

    public void setTimemodified(long timemodified) {
        this.timemodified = timemodified;
    }

    public long getGrader() {
        return grader;
    }

    public void setGrader(long grader) {
        this.grader = grader;
    }

    public String getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(String gradeValue) {
        this.gradeValue = gradeValue;
    }
}
