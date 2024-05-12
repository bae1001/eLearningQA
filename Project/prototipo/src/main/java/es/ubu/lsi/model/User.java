package es.ubu.lsi.model;

import java.util.Comparator;
import java.util.List;

public class User {
    private int id;
    private String username;
    private String firstname;
    private String lastname;
    private String fullname;
    private String email;
    private String phone1;
    private String phone2;
    private String department;
    private String interests;
    private int firstaccess;
    private int lastaccess;
    private int lastcourseaccess;
    private String description;
    private int descriptionformat;
    private String city;
    private String country;
    private String profileimageurlsmall;
    private String profileimageurl;
    private List<Group> groups;
    private List<Role> roles;
    private List<Preference> preferences;
    private List<Course> enrolledcourses;
    private boolean started;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public int getFirstaccess() {
        return firstaccess;
    }

    public void setFirstaccess(int firstaccess) {
        this.firstaccess = firstaccess;
    }

    public int getLastaccess() {
        return lastaccess;
    }

    public void setLastaccess(int lastaccess) {
        this.lastaccess = lastaccess;
    }

    public int getLastcourseaccess() {
        return lastcourseaccess;
    }

    public void setLastcourseaccess(int lastcourseaccess) {
        this.lastcourseaccess = lastcourseaccess;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProfileimageurlsmall() {
        return profileimageurlsmall;
    }

    public void setProfileimageurlsmall(String profileimageurlsmall) {
        this.profileimageurlsmall = profileimageurlsmall;
    }

    public String getProfileimageurl() {
        return profileimageurl;
    }

    public void setProfileimageurl(String profileimageurl) {
        this.profileimageurl = profileimageurl;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Preference> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<Preference> preferences) {
        this.preferences = preferences;
    }

    public List<Course> getEnrolledcourses() {
        return enrolledcourses;
    }

    public void setEnrolledcourses(List<Course> enrolledcourses) {
        this.enrolledcourses = enrolledcourses;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public static Comparator<User> UserNameComparator = new Comparator<User>() {

        public int compare(User firsUser, User secondUser) {
            String firsUserName = firsUser.getFirstname().toUpperCase();
            String secondUserName = secondUser.getFirstname().toUpperCase();

            return firsUserName.compareTo(
                    secondUserName);
        }
    };
}