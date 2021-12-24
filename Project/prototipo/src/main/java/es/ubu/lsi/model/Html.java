package es.ubu.lsi.model;

public class Html{
    private Object rating;
    private String taglist;
    private String authorsubheading;

    public Html() {}

    public Object getRating() {
        return rating;
    }

    public void setRating(Object rating) {
        this.rating = rating;
    }

    public String getTaglist() {
        return taglist;
    }

    public void setTaglist(String taglist) {
        this.taglist = taglist;
    }

    public String getAuthorsubheading() {
        return authorsubheading;
    }

    public void setAuthorsubheading(String authorsubheading) {
        this.authorsubheading = authorsubheading;
    }
}
