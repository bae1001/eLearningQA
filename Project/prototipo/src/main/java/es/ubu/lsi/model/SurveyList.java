package es.ubu.lsi.model;

import java.util.List;

public class SurveyList {
    private List<Survey> surveys;
    private List<Object> warnings;

    public SurveyList() {}


    public List<Survey> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<Survey> surveys) {
        this.surveys = surveys;
    }

    public List<Object> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }
}

