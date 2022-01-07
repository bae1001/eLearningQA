package es.ubu.lsi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GradeTableField{
    @JsonProperty("class")
    private String myclass;
    private String content;
    private String headers;

    public GradeTableField() {}


    public String getMyclass() {
        return myclass;
    }

    public void setMyclass(String myclass) {
        this.myclass = myclass;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }
}
