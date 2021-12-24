package es.ubu.lsi.model;

import java.util.List;

public class PostList{
    private List<Post> posts;
    private int forumid;
    private int courseid;
    private Ratinginfo ratinginfo;
    private List<Object> warnings;

    public PostList() {}


    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public int getForumid() {
        return forumid;
    }

    public void setForumid(int forumid) {
        this.forumid = forumid;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public Ratinginfo getRatinginfo() {
        return ratinginfo;
    }

    public void setRatinginfo(Ratinginfo ratinginfo) {
        this.ratinginfo = ratinginfo;
    }

    public List<Object> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }
}
