package es.ubu.lsi.model;

import java.util.List;

public class PostList{
    private List<Post> posts;
    private long forumid;
    private long courseid;
    private Ratinginfo ratinginfo;
    private List<Object> warnings;


    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public long getForumid() {
        return forumid;
    }

    public void setForumid(long forumid) {
        this.forumid = forumid;
    }

    public long getCourseid() {
        return courseid;
    }

    public void setCourseid(long courseid) {
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
