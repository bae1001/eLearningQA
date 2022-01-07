package es.ubu.lsi.model;

import java.util.List;

public class Survey{
    private int id;
    private int coursemodule;
    private int course;
    private String name;
    private String intro;
    private int introformat;
    private List<Introfile> introfiles;
    private int template;
    private int days;
    private String questions;
    private int surveydone;
    private int timecreated;
    private int timemodified;
    private int section;
    private int visible;
    private int groupmode;
    private int groupingid;

    public Survey() {}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoursemodule() {
        return coursemodule;
    }

    public void setCoursemodule(int coursemodule) {
        this.coursemodule = coursemodule;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getIntroformat() {
        return introformat;
    }

    public void setIntroformat(int introformat) {
        this.introformat = introformat;
    }

    public List<Introfile> getIntrofiles() {
        return introfiles;
    }

    public void setIntrofiles(List<Introfile> introfiles) {
        this.introfiles = introfiles;
    }

    public int getTemplate() {
        return template;
    }

    public void setTemplate(int template) {
        this.template = template;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public int getSurveydone() {
        return surveydone;
    }

    public void setSurveydone(int surveydone) {
        this.surveydone = surveydone;
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

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getGroupmode() {
        return groupmode;
    }

    public void setGroupmode(int groupmode) {
        this.groupmode = groupmode;
    }

    public int getGroupingid() {
        return groupingid;
    }

    public void setGroupingid(int groupingid) {
        this.groupingid = groupingid;
    }
}
