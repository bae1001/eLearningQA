package es.ubu.lsi.model;

import java.util.List;

public class AssignmentList {
    private List<Assignment> assignments;
    private List<Object> warnings;


    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public List<Object> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }
}
