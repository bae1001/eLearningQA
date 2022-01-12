package es.ubu.lsi.model;

import java.util.List;

public class Feedback{
    private int id;
    private int course;
    private String name;
    private String intro;
    private int introformat;
    private int anonymous;
    private boolean email_notification;
    private boolean multiple_submit;
    private boolean autonumbering;
    private String site_after_submit;
    private String page_after_submit;
    private int page_after_submitformat;
    private boolean publish_stats;
    private int timeopen;
    private int timeclose;
    private int timemodified;
    private boolean completionsubmit;
    private int coursemodule;
    private List<Introfile> introfiles;
    private List<Object> pageaftersubmitfiles;

    public Feedback() {}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(int anonymous) {
        this.anonymous = anonymous;
    }

    public boolean isEmail_notification() {
        return email_notification;
    }

    public void setEmail_notification(boolean email_notification) {
        this.email_notification = email_notification;
    }

    public boolean isMultiple_submit() {
        return multiple_submit;
    }

    public void setMultiple_submit(boolean multiple_submit) {
        this.multiple_submit = multiple_submit;
    }

    public boolean isAutonumbering() {
        return autonumbering;
    }

    public void setAutonumbering(boolean autonumbering) {
        this.autonumbering = autonumbering;
    }

    public String getSite_after_submit() {
        return site_after_submit;
    }

    public void setSite_after_submit(String site_after_submit) {
        this.site_after_submit = site_after_submit;
    }

    public String getPage_after_submit() {
        return page_after_submit;
    }

    public void setPage_after_submit(String page_after_submit) {
        this.page_after_submit = page_after_submit;
    }

    public int getPage_after_submitformat() {
        return page_after_submitformat;
    }

    public void setPage_after_submitformat(int page_after_submitformat) {
        this.page_after_submitformat = page_after_submitformat;
    }

    public boolean isPublish_stats() {
        return publish_stats;
    }

    public void setPublish_stats(boolean publish_stats) {
        this.publish_stats = publish_stats;
    }

    public int getTimeopen() {
        return timeopen;
    }

    public void setTimeopen(int timeopen) {
        this.timeopen = timeopen;
    }

    public int getTimeclose() {
        return timeclose;
    }

    public void setTimeclose(int timeclose) {
        this.timeclose = timeclose;
    }

    public int getTimemodified() {
        return timemodified;
    }

    public void setTimemodified(int timemodified) {
        this.timemodified = timemodified;
    }

    public boolean isCompletionsubmit() {
        return completionsubmit;
    }

    public void setCompletionsubmit(boolean completionsubmit) {
        this.completionsubmit = completionsubmit;
    }

    public int getCoursemodule() {
        return coursemodule;
    }

    public void setCoursemodule(int coursemodule) {
        this.coursemodule = coursemodule;
    }

    public List<Introfile> getIntrofiles() {
        return introfiles;
    }

    public void setIntrofiles(List<Introfile> introfiles) {
        this.introfiles = introfiles;
    }

    public List<Object> getPageaftersubmitfiles() {
        return pageaftersubmitfiles;
    }

    public void setPageaftersubmitfiles(List<Object> pageaftersubmitfiles) {
        this.pageaftersubmitfiles = pageaftersubmitfiles;
    }
}