package es.ubu.lsi.model;

import java.util.List;

public class Feedback extends MoodleObject {
    private int anonymous;
    private boolean emailNotification;
    private boolean multipleSubmit;
    private boolean autonumbering;
    private String siteAfterSubmit;
    private String pageAfterSubmit;
    private int pageAfterSubmitformat;
    private boolean publishStats;
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
        return emailNotification;
    }

    public void setEmailnotification(boolean emailNotification) {
        this.emailNotification = emailNotification;
    }

    public boolean isMultiplesubmit() {
        return multipleSubmit;
    }

    public void setMultiplesubmit(boolean multipleSubmit) {
        this.multipleSubmit = multipleSubmit;
    }

    public boolean isAutonumbering() {
        return autonumbering;
    }

    public void setAutonumbering(boolean autonumbering) {
        this.autonumbering = autonumbering;
    }

    public String getSiteaftersubmit() {
        return siteAfterSubmit;
    }

    public void setSiteaftersubmit(String siteAfterSubmit) {
        this.siteAfterSubmit = siteAfterSubmit;
    }

    public String getPageaftersubmit() {
        return pageAfterSubmit;
    }

    public void setPageaftersubmit(String pageAfterSubmit) {
        this.pageAfterSubmit = pageAfterSubmit;
    }

    public int getPageaftersubmitformat() {
        return pageAfterSubmitformat;
    }

    public void setPageaftersubmitformat(int pageAfterSubmitformat) {
        this.pageAfterSubmitformat = pageAfterSubmitformat;
    }

    public boolean isPublishstats() {
        return publishStats;
    }

    public void setPublishstats(boolean publishStats) {
        this.publishStats = publishStats;
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
