package es.ubu.lsi.model;

import java.util.List;

public class ResponseAnalysis {
    private List<AnalysisAttempt> attempts;
    private int totalattempts;
    private List<Anonattempt> anonattempts;
    private int totalanonattempts;
    private List<Object> warnings;

    public List<AnalysisAttempt> getAttempts() {
        return attempts;
    }

    public void setAttempts(List<AnalysisAttempt> attempts) {
        this.attempts = attempts;
    }

    public int getTotalattempts() {
        return totalattempts;
    }

    public void setTotalattempts(int totalattempts) {
        this.totalattempts = totalattempts;
    }

    public List<Anonattempt> getAnonattempts() {
        return anonattempts;
    }

    public void setAnonattempts(List<Anonattempt> anonattempts) {
        this.anonattempts = anonattempts;
    }

    public int getTotalanonattempts() {
        return totalanonattempts;
    }

    public void setTotalanonattempts(int totalanonattempts) {
        this.totalanonattempts = totalanonattempts;
    }

    public List<Object> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }
}
