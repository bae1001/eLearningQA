package es.ubu.lsi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GradeTableField extends MoodleObject{
    @JsonProperty("class")
    private String myclass;
    private String headers;


    public String getMyclass() {
        return myclass;
    }

    public void setMyclass(String myclass) {
        this.myclass = myclass;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }
}
