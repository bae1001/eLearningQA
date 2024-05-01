package es.ubu.lsi.model;

public class Quiz {
    private String id;
    private String coursemodule;
    private String course;
    private String timeclose;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoursemodule() {
        return coursemodule;
    }

    public void setCoursemodule(String coursemodule) {
        this.coursemodule = coursemodule;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTimeclose() {
        return timeclose;
    }

    public void setTimeclose(String timeclose) {
        this.timeclose = timeclose;
    }
}
