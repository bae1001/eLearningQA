package es.ubu.lsi.model;

import java.util.List;

public class FeedbackList{
    private List<Feedback> feedbacks;
    private List<Object> warnings;


    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public List<Object> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }
}
