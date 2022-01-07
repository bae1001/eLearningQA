package es.ubu.lsi.model;

import java.util.List;

public class Contentsinfo{
    private int filescount;
    private int filessize;
    private int lastmodified;
    private List<String> mimetypes;
    private String repositorytype;

    public Contentsinfo() {}


    public int getFilescount() {
        return filescount;
    }

    public void setFilescount(int filescount) {
        this.filescount = filescount;
    }

    public int getFilessize() {
        return filessize;
    }

    public void setFilessize(int filessize) {
        this.filessize = filessize;
    }

    public int getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(int lastmodified) {
        this.lastmodified = lastmodified;
    }

    public List<String> getMimetypes() {
        return mimetypes;
    }

    public void setMimetypes(List<String> mimetypes) {
        this.mimetypes = mimetypes;
    }

    public String getRepositorytype() {
        return repositorytype;
    }

    public void setRepositorytype(String repositorytype) {
        this.repositorytype = repositorytype;
    }
}
