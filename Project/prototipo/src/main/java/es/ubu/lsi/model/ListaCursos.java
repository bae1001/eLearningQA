package es.ubu.lsi.model;

import java.util.List;

public class ListaCursos {
    private List<Curso> courses;
    private int nextoffset;

    public ListaCursos() {}

    public List<Curso> getCourses() {
        return courses;
    }

    public void setCourses(List<Curso> courses) {
        this.courses = courses;
    }

    public int getNextoffset() {
        return nextoffset;
    }

    public void setNextoffset(int nextoffset) {
        this.nextoffset = nextoffset;
    }
}
