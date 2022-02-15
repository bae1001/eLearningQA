package es.ubu.lsi.model;

import java.util.List;

public class Ratinginfo{
    private int contextid;
    private String component;
    private String ratingarea;
    private Object canviewall;
    private Object canviewany;
    private List<Object> scales;
    private List<Object> ratings;


    public int getContextid() {
        return contextid;
    }

    public void setContextid(int contextid) {
        this.contextid = contextid;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRatingarea() {
        return ratingarea;
    }

    public void setRatingarea(String ratingarea) {
        this.ratingarea = ratingarea;
    }

    public Object getCanviewall() {
        return canviewall;
    }

    public void setCanviewall(Object canviewall) {
        this.canviewall = canviewall;
    }

    public Object getCanviewany() {
        return canviewany;
    }

    public void setCanviewany(Object canviewany) {
        this.canviewany = canviewany;
    }

    public List<Object> getScales() {
        return scales;
    }

    public void setScales(List<Object> scales) {
        this.scales = scales;
    }

    public List<Object> getRatings() {
        return ratings;
    }

    public void setRatings(List<Object> ratings) {
        this.ratings = ratings;
    }
}
