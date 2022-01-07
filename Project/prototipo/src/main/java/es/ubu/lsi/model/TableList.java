package es.ubu.lsi.model;

import java.util.List;

public class TableList{
    private List<Table> tables;
    private List<Object> warnings;

    public TableList() {}


    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public List<Object> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }
}
