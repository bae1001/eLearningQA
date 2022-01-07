package es.ubu.lsi.model;

import java.util.List;

public class NonRespondentList{
    private List<User> users;
    private int total;
    private List<Object> warnings;

    public NonRespondentList() {}


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Object> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }
}
