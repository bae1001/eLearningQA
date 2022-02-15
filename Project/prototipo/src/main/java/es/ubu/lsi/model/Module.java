package es.ubu.lsi.model;

import java.util.List;

public class Module{
    private int id;
    private String url;
    private String name;
    private int instance;
    private int contextid;
    private int visible;
    private boolean uservisible;
    private int visibleoncoursepage;
    private String modicon;
    private String modname;
    private String modplural;
    private Object availability;
    private int indent;
    private String onclick;
    private String afterlink;
    private String customdata;
    private boolean noviewlink;
    private int completion;
    private List<Date> dates;
    private Completiondata completiondata;
    private List<Content> contents;
    private Contentsinfo contentsinfo;
    private String description;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInstance() {
        return instance;
    }

    public void setInstance(int instance) {
        this.instance = instance;
    }

    public int getContextid() {
        return contextid;
    }

    public void setContextid(int contextid) {
        this.contextid = contextid;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public boolean isUservisible() {
        return uservisible;
    }

    public void setUservisible(boolean uservisible) {
        this.uservisible = uservisible;
    }

    public int getVisibleoncoursepage() {
        return visibleoncoursepage;
    }

    public void setVisibleoncoursepage(int visibleoncoursepage) {
        this.visibleoncoursepage = visibleoncoursepage;
    }

    public String getModicon() {
        return modicon;
    }

    public void setModicon(String modicon) {
        this.modicon = modicon;
    }

    public String getModname() {
        return modname;
    }

    public void setModname(String modname) {
        this.modname = modname;
    }

    public String getModplural() {
        return modplural;
    }

    public void setModplural(String modplural) {
        this.modplural = modplural;
    }

    public Object getAvailability() {
        return availability;
    }

    public void setAvailability(Object availability) {
        this.availability = availability;
    }

    public int getIndent() {
        return indent;
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getAfterlink() {
        return afterlink;
    }

    public void setAfterlink(String afterlink) {
        this.afterlink = afterlink;
    }

    public String getCustomdata() {
        return customdata;
    }

    public void setCustomdata(String customdata) {
        this.customdata = customdata;
    }

    public boolean isNoviewlink() {
        return noviewlink;
    }

    public void setNoviewlink(boolean noviewlink) {
        this.noviewlink = noviewlink;
    }

    public int getCompletion() {
        return completion;
    }

    public void setCompletion(int completion) {
        this.completion = completion;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    public Completiondata getCompletiondata() {
        return completiondata;
    }

    public void setCompletiondata(Completiondata completiondata) {
        this.completiondata = completiondata;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public Contentsinfo getContentsinfo() {
        return contentsinfo;
    }

    public void setContentsinfo(Contentsinfo contentsinfo) {
        this.contentsinfo = contentsinfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
