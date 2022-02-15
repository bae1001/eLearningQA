package es.ubu.lsi.model;

import java.util.List;

public class DiscussionList{
    private List<Discussion> discussions;
    private List<Object> warnings;


    public List<Discussion> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<Discussion> discussions) {
        this.discussions = discussions;
    }

    public List<Object> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }
}
