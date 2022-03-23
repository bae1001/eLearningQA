package es.ubu.lsi.model;

import java.util.List;

public class Attempt{
    private long id;
    private long courseid;
    private long userid;
    private long timemodified;
    private String fullname;
    private List<Response> responses;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public long getTimemodified() {
        return timemodified;
    }

    public void setTimemodified(long timemodified) {
        this.timemodified = timemodified;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }
}
