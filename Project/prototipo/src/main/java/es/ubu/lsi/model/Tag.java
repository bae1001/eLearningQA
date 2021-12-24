package es.ubu.lsi.model;

public class Tag{
    private int id;
    private int tagid;
    private boolean isstandard;
    private String displayname;
    private boolean flag;
    private Urls urls;

    public Tag() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTagid() {
        return tagid;
    }

    public void setTagid(int tagid) {
        this.tagid = tagid;
    }

    public boolean isIsstandard() {
        return isstandard;
    }

    public void setIsstandard(boolean isstandard) {
        this.isstandard = isstandard;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }
}
