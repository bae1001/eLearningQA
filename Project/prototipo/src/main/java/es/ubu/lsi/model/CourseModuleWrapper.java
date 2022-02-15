package es.ubu.lsi.model;

import java.util.List;

public class CourseModuleWrapper{
    private CourseModule cm;
    private List<Object> warnings;


    public CourseModule getCm() {
        return cm;
    }

    public void setCm(CourseModule cm) {
        this.cm = cm;
    }

    public List<Object> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }
}
