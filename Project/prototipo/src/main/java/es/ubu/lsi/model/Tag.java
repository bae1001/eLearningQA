package es.ubu.lsi.model;

public class Tag{
    private int id;
    private int tagid;
    private boolean isstandard;
    private String displayname;
    private boolean flag;
    private Urls urls;
    private String name;
    private String rawname;
    private int tagcollid;
    private int taginstanceid;
    private int taginstancecontextid;
    private int itemid;
    private int ordering;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRawname() {
        return rawname;
    }

    public void setRawname(String rawname) {
        this.rawname = rawname;
    }

    public int getTagcollid() {
        return tagcollid;
    }

    public void setTagcollid(int tagcollid) {
        this.tagcollid = tagcollid;
    }

    public int getTaginstanceid() {
        return taginstanceid;
    }

    public void setTaginstanceid(int taginstanceid) {
        this.taginstanceid = taginstanceid;
    }

    public int getTaginstancecontextid() {
        return taginstancecontextid;
    }

    public void setTaginstancecontextid(int taginstancecontextid) {
        this.taginstancecontextid = taginstancecontextid;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }
}
