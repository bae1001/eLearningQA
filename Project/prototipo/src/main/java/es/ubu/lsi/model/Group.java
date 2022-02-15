package es.ubu.lsi.model;

public class Group {
    private int id;
    private int courseid;
    private String name;
    private String description;
    private int descriptionformat;
    private String enrolmentkey;
    private String idnumber;
    private Urls urls;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDescriptionformat() {
        return descriptionformat;
    }

    public void setDescriptionformat(int descriptionformat) {
        this.descriptionformat = descriptionformat;
    }

    public String getEnrolmentkey() {
        return enrolmentkey;
    }

    public void setEnrolmentkey(String enrolmentkey) {
        this.enrolmentkey = enrolmentkey;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }
}
