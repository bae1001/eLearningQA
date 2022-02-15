package es.ubu.lsi.model;

import java.util.List;

public class CourseList {
    private List<Course> courses;
    private int nextoffset;
    private List<Object> warnings;


    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public int getNextoffset() {
        return nextoffset;
    }

    public void setNextoffset(int nextoffset) {
        this.nextoffset = nextoffset;
    }

    public List<Object> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }
}
