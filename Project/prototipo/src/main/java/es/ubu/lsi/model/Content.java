package es.ubu.lsi.model;

import java.util.List;

public class Content extends MoodleObject {
    private String type;
    private long timecreated;
    private int sortorder;
    private long userid;
    private String author;
    private String license;
    private List<Tag> tags;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTimecreated() {
        return timecreated;
    }

    public void setTimecreated(long timecreated) {
        this.timecreated = timecreated;
    }

    public int getSortorder() {
        return sortorder;
    }

    public void setSortorder(int sortorder) {
        this.sortorder = sortorder;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
