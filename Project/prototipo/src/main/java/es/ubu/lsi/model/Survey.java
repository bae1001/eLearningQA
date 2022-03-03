package es.ubu.lsi.model;

public class Survey extends MoodleObject {
    private int template;
    private int days;
    private String questions;
    private int surveydone;
    private int timecreated;


    public int getTemplate() {
        return template;
    }

    public void setTemplate(int template) {
        this.template = template;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public int getSurveydone() {
        return surveydone;
    }

    public void setSurveydone(int surveydone) {
        this.surveydone = surveydone;
    }

    public int getTimecreated() {
        return timecreated;
    }

    public void setTimecreated(int timecreated) {
        this.timecreated = timecreated;
    }

}
