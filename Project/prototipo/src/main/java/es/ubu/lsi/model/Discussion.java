package es.ubu.lsi.model;

public class Discussion{
    private int id;
    private String name;
    private int groupid;
    private int timemodified;
    private int usermodified;
    private int timestart;
    private int timeend;
    private int discussion;
    private int parent;
    private int userid;
    private int created;
    private int modified;
    private int mailed;
    private String subject;
    private String message;
    private int messageformat;
    private int messagetrust;
    private boolean attachment;
    private int totalscore;
    private int mailnow;
    private String userfullname;
    private String usermodifiedfullname;
    private String userpictureurl;
    private String usermodifiedpictureurl;
    private int numreplies;
    private int numunread;
    private boolean pinned;
    private boolean locked;
    private boolean starred;
    private boolean canreply;
    private boolean canlock;
    private boolean canfavourite;

    public Discussion() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public int getTimemodified() {
        return timemodified;
    }

    public void setTimemodified(int timemodified) {
        this.timemodified = timemodified;
    }

    public int getUsermodified() {
        return usermodified;
    }

    public void setUsermodified(int usermodified) {
        this.usermodified = usermodified;
    }

    public int getTimestart() {
        return timestart;
    }

    public void setTimestart(int timestart) {
        this.timestart = timestart;
    }

    public int getTimeend() {
        return timeend;
    }

    public void setTimeend(int timeend) {
        this.timeend = timeend;
    }

    public int getDiscussion() {
        return discussion;
    }

    public void setDiscussion(int discussion) {
        this.discussion = discussion;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public int getModified() {
        return modified;
    }

    public void setModified(int modified) {
        this.modified = modified;
    }

    public int getMailed() {
        return mailed;
    }

    public void setMailed(int mailed) {
        this.mailed = mailed;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public int getMessagetrust() {
        return messagetrust;
    }

    public void setMessagetrust(int messagetrust) {
        this.messagetrust = messagetrust;
    }

    public boolean isAttachment() {
        return attachment;
    }

    public void setAttachment(boolean attachment) {
        this.attachment = attachment;
    }

    public int getTotalscore() {
        return totalscore;
    }

    public void setTotalscore(int totalscore) {
        this.totalscore = totalscore;
    }

    public int getMailnow() {
        return mailnow;
    }

    public void setMailnow(int mailnow) {
        this.mailnow = mailnow;
    }

    public String getUserfullname() {
        return userfullname;
    }

    public void setUserfullname(String userfullname) {
        this.userfullname = userfullname;
    }

    public String getUsermodifiedfullname() {
        return usermodifiedfullname;
    }

    public void setUsermodifiedfullname(String usermodifiedfullname) {
        this.usermodifiedfullname = usermodifiedfullname;
    }

    public String getUserpictureurl() {
        return userpictureurl;
    }

    public void setUserpictureurl(String userpictureurl) {
        this.userpictureurl = userpictureurl;
    }

    public String getUsermodifiedpictureurl() {
        return usermodifiedpictureurl;
    }

    public void setUsermodifiedpictureurl(String usermodifiedpictureurl) {
        this.usermodifiedpictureurl = usermodifiedpictureurl;
    }

    public int getNumreplies() {
        return numreplies;
    }

    public void setNumreplies(int numreplies) {
        this.numreplies = numreplies;
    }

    public int getNumunread() {
        return numunread;
    }

    public void setNumunread(int numunread) {
        this.numunread = numunread;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    public boolean isCanreply() {
        return canreply;
    }

    public void setCanreply(boolean canreply) {
        this.canreply = canreply;
    }

    public boolean isCanlock() {
        return canlock;
    }

    public void setCanlock(boolean canlock) {
        this.canlock = canlock;
    }

    public boolean isCanfavourite() {
        return canfavourite;
    }

    public void setCanfavourite(boolean canfavourite) {
        this.canfavourite = canfavourite;
    }
}
