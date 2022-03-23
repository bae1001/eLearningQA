package es.ubu.lsi.model;

public class Forum extends MoodleObject {
    private String type;
    private long duedate;
    private long cutoffdate;
    private long assessed;
    private long assesstimestart;
    private long assesstimefinish;
    private int scale;
    private int gradeForum;
    private int gradeForumNotify;
    private int maxbytes;
    private int maxattachments;
    private int forcesubscribe;
    private int trackingtype;
    private int rsstype;
    private int rssarticles;
    private int warnafter;
    private int blockafter;
    private int blockperiod;
    private int completiondiscussions;
    private int completionreplies;
    private int completionposts;
    private long cmid;
    private int numdiscussions;
    private boolean cancreatediscussions;
    private int lockdiscussionafter;
    private boolean istracked;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getDuedate() {
        return duedate;
    }

    public void setDuedate(long duedate) {
        this.duedate = duedate;
    }

    public long getCutoffdate() {
        return cutoffdate;
    }

    public void setCutoffdate(long cutoffdate) {
        this.cutoffdate = cutoffdate;
    }

    public long getAssessed() {
        return assessed;
    }

    public void setAssessed(long assessed) {
        this.assessed = assessed;
    }

    public long getAssesstimestart() {
        return assesstimestart;
    }

    public void setAssesstimestart(long assesstimestart) {
        this.assesstimestart = assesstimestart;
    }

    public long getAssesstimefinish() {
        return assesstimefinish;
    }

    public void setAssesstimefinish(long assesstimefinish) {
        this.assesstimefinish = assesstimefinish;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getGradeforum() {
        return gradeForum;
    }

    public void setGradeforum(int gradeForum) {
        this.gradeForum = gradeForum;
    }

    public int getGradeforumnotify() {
        return gradeForumNotify;
    }

    public void setGradeforumnotify(int gradeForumNotify) {
        this.gradeForumNotify = gradeForumNotify;
    }

    public int getMaxbytes() {
        return maxbytes;
    }

    public void setMaxbytes(int maxbytes) {
        this.maxbytes = maxbytes;
    }

    public int getMaxattachments() {
        return maxattachments;
    }

    public void setMaxattachments(int maxattachments) {
        this.maxattachments = maxattachments;
    }

    public int getForcesubscribe() {
        return forcesubscribe;
    }

    public void setForcesubscribe(int forcesubscribe) {
        this.forcesubscribe = forcesubscribe;
    }

    public int getTrackingtype() {
        return trackingtype;
    }

    public void setTrackingtype(int trackingtype) {
        this.trackingtype = trackingtype;
    }

    public int getRsstype() {
        return rsstype;
    }

    public void setRsstype(int rsstype) {
        this.rsstype = rsstype;
    }

    public int getRssarticles() {
        return rssarticles;
    }

    public void setRssarticles(int rssarticles) {
        this.rssarticles = rssarticles;
    }

    public int getWarnafter() {
        return warnafter;
    }

    public void setWarnafter(int warnafter) {
        this.warnafter = warnafter;
    }

    public int getBlockafter() {
        return blockafter;
    }

    public void setBlockafter(int blockafter) {
        this.blockafter = blockafter;
    }

    public int getBlockperiod() {
        return blockperiod;
    }

    public void setBlockperiod(int blockperiod) {
        this.blockperiod = blockperiod;
    }

    public int getCompletiondiscussions() {
        return completiondiscussions;
    }

    public void setCompletiondiscussions(int completiondiscussions) {
        this.completiondiscussions = completiondiscussions;
    }

    public int getCompletionreplies() {
        return completionreplies;
    }

    public void setCompletionreplies(int completionreplies) {
        this.completionreplies = completionreplies;
    }

    public int getCompletionposts() {
        return completionposts;
    }

    public void setCompletionposts(int completionposts) {
        this.completionposts = completionposts;
    }

    public long getCmid() {
        return cmid;
    }

    public void setCmid(long cmid) {
        this.cmid = cmid;
    }

    public int getNumdiscussions() {
        return numdiscussions;
    }

    public void setNumdiscussions(int numdiscussions) {
        this.numdiscussions = numdiscussions;
    }

    public boolean isCancreatediscussions() {
        return cancreatediscussions;
    }

    public void setCancreatediscussions(boolean cancreatediscussions) {
        this.cancreatediscussions = cancreatediscussions;
    }

    public int getLockdiscussionafter() {
        return lockdiscussionafter;
    }

    public void setLockdiscussionafter(int lockdiscussionafter) {
        this.lockdiscussionafter = lockdiscussionafter;
    }

    public boolean isIstracked() {
        return istracked;
    }

    public void setIstracked(boolean istracked) {
        this.istracked = istracked;
    }
}
