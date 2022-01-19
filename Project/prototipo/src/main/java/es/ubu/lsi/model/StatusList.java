package es.ubu.lsi.model;

import java.util.List;

public class StatusList {
    private List<Status> statuses;
    private List<Object> warnings;

    public StatusList() {}

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    public List<Object> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }

    /*
    public boolean isHasCompletion(){
        if(statuses.size()==0){return true;}
        return statuses.get(0).isHascompletion();
    }

     */



}


