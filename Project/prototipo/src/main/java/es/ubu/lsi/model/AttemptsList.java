package es.ubu.lsi.model;

import java.util.List;

public class AttemptsList {
    private List<QuizzAttempt> attempts;
    private List<Object> warnings;

    public List<QuizzAttempt> getAttempts() {
        return attempts;
    }

    public void setAttempts(List<QuizzAttempt> attempts) {
        this.attempts = attempts;
    }

    public List<Object> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }
}
