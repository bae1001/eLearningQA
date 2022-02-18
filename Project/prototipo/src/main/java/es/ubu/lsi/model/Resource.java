package es.ubu.lsi.model;

import java.util.List;

public class Resource extends SuperClass{
    private List<Contentfile> contentfiles;
    private int tobemigrated;
    private int legacyfiles;
    private Object legacyfileslast;
    private int display;
    private String displayoptions;
    private int filterfiles;
    private int revision;

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
}
