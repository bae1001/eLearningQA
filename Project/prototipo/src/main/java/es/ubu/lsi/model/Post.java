package es.ubu.lsi.model;

import java.util.List;

public class Post{
    private int id;
    private String subject;
    private String replysubject;
    private String message;
    private int messageformat;
    private Author author;
    private int discussionid;
    private boolean hasparent;
    private int parentid;
    private int timecreated;
    private int timemodified;
    private Object unread;
    private boolean isdeleted;
    private boolean isprivatereply;
    private boolean haswordcount;
    private Object wordcount;
    private Object charcount;
    private Capabilities capabilities;
    private Urls urls;
    private List<Object> attachments;
    private List<Tag> tags;
    private Html html;

    public Post() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReplysubject() {
        return replysubject;
    }

    public void setReplysubject(String replysubject) {
        this.replysubject = replysubject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessageformat() {
        return messageformat;
    }

    public void setMessageformat(int messageformat) {
        this.messageformat = messageformat;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getDiscussionid() {
        return discussionid;
    }

    public void setDiscussionid(int discussionid) {
        this.discussionid = discussionid;
    }

    public boolean isHasparent() {
        return hasparent;
    }

    public void setHasparent(boolean hasparent) {
        this.hasparent = hasparent;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public int getTimecreated() {
        return timecreated;
    }

    public void setTimecreated(int timecreated) {
        this.timecreated = timecreated;
    }

    public int getTimemodified() {
        return timemodified;
    }

    public void setTimemodified(int timemodified) {
        this.timemodified = timemodified;
    }

    public Object getUnread() {
        return unread;
    }

    public void setUnread(Object unread) {
        this.unread = unread;
    }

    public boolean isIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public boolean isIsprivatereply() {
        return isprivatereply;
    }

    public void setIsprivatereply(boolean isprivatereply) {
        this.isprivatereply = isprivatereply;
    }

    public boolean isHaswordcount() {
        return haswordcount;
    }

    public void setHaswordcount(boolean haswordcount) {
        this.haswordcount = haswordcount;
    }

    public Object getWordcount() {
        return wordcount;
    }

    public void setWordcount(Object wordcount) {
        this.wordcount = wordcount;
    }

    public Object getCharcount() {
        return charcount;
    }

    public void setCharcount(Object charcount) {
        this.charcount = charcount;
    }

    public Capabilities getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Capabilities capabilities) {
        this.capabilities = capabilities;
    }

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public List<Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Object> attachments) {
        this.attachments = attachments;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Html getHtml() {
        return html;
    }

    public void setHtml(Html html) {
        this.html = html;
    }
}

