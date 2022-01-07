package es.ubu.lsi.model;

import java.util.List;

public class Resource{
    private int id;
    private int coursemodule;
    private int course;
    private String name;
    private String intro;
    private int introformat;
    private List<Object> introfiles;
    private List<Contentfile> contentfiles;
    private int tobemigrated;
    private int legacyfiles;
    private Object legacyfileslast;
    private int display;
    private String displayoptions;
    private int filterfiles;
    private int revision;
    private int timemodified;
    private int section;
    private int visible;
    private int groupmode;
    private int groupingid;

    public Resource() {}


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

    public List<Object> getIntrofiles() {
        return introfiles;
    }

    public void setIntrofiles(List<Object> introfiles) {
        this.introfiles = introfiles;
    }

    public List<Contentfile> getContentfiles() {
        return contentfiles;
    }

    public void setContentfiles(List<Contentfile> contentfiles) {
        this.contentfiles = contentfiles;
    }

    public int getTobemigrated() {
        return tobemigrated;
    }

    public void setTobemigrated(int tobemigrated) {
        this.tobemigrated = tobemigrated;
    }

    public int getLegacyfiles() {
        return legacyfiles;
    }

    public void setLegacyfiles(int legacyfiles) {
        this.legacyfiles = legacyfiles;
    }

    public Object getLegacyfileslast() {
        return legacyfileslast;
    }

    public void setLegacyfileslast(Object legacyfileslast) {
        this.legacyfileslast = legacyfileslast;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public String getDisplayoptions() {
        return displayoptions;
    }

    public void setDisplayoptions(String displayoptions) {
        this.displayoptions = displayoptions;
    }

    public int getFilterfiles() {
        return filterfiles;
    }

    public void setFilterfiles(int filterfiles) {
        this.filterfiles = filterfiles;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
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
