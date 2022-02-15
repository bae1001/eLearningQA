package es.ubu.lsi.model;

import java.util.List;

public class ResourceList {
    private List<Resource> resources;
    private List<Object> warnings;


    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public List<Object> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }
}
