package es.ubu.lsi.model;

import java.util.List;

public class Feedback extends SuperClass{
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
    private boolean completionsubmit;
    private List<Object> pageaftersubmitfiles;


    public int getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(int anonymous) {
        this.anonymous = anonymous;
    }

    public boolean isEmailnotification() {
        return email_notification;
    }

    public void setEmailnotification(boolean email_notification) {
        this.email_notification = email_notification;
    }

    public boolean isMultiplesubmit() {
        return multiple_submit;
    }

    public void setMultiplesubmit(boolean multiple_submit) {
        this.multiple_submit = multiple_submit;
    }

    public boolean isAutonumbering() {
        return autonumbering;
    }

    public void setAutonumbering(boolean autonumbering) {
        this.autonumbering = autonumbering;
    }

    public String getSiteaftersubmit() {
        return site_after_submit;
    }

    public void setSiteaftersubmit(String site_after_submit) {
        this.site_after_submit = site_after_submit;
    }

    public String getPageaftersubmit() {
        return page_after_submit;
    }

    public void setPageaftersubmit(String page_after_submit) {
        this.page_after_submit = page_after_submit;
    }

    public int getPageaftersubmitformat() {
        return page_after_submitformat;
    }

    public void setPageaftersubmitformat(int page_after_submitformat) {
        this.page_after_submitformat = page_after_submitformat;
    }

    public boolean isPublishstats() {
        return publish_stats;
    }

    public void setPublishstats(boolean publish_stats) {
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

    public boolean isCompletionsubmit() {
        return completionsubmit;
    }

    public void setCompletionsubmit(boolean completionsubmit) {
        this.completionsubmit = completionsubmit;
    }

    public List<Object> getPageaftersubmitfiles() {
        return pageaftersubmitfiles;
    }

    public void setPageaftersubmitfiles(List<Object> pageaftersubmitfiles) {
        this.pageaftersubmitfiles = pageaftersubmitfiles;
    }
}
