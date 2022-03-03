package es.ubu.lsi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Grade{
    private int id;
    private int userid;
    private int attemptnumber;
    private int timecreated;
    private int timemodified;
    private int grader;
    @JsonProperty("grade")
    private String gradeValue;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getAttemptnumber() {
        return attemptnumber;
    }

    public void setAttemptnumber(int attemptnumber) {
        this.attemptnumber = attemptnumber;
    }

    public int getTimecreated() {
        return timecreated;
    }

    public void setTimecreated(int timecreated) {
        this.timecreated = timecreated;
    }

    public int getTimemodified() {
        return timemodified;
    }

    public void setTimemodified(int timemodified) {
        this.timemodified = timemodified;
    }

    public int getGrader() {
        return grader;
    }

    public void setGrader(int grader) {
        this.grader = grader;
    }

    public String getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(String gradeValue) {
        this.gradeValue = gradeValue;
    }
}
