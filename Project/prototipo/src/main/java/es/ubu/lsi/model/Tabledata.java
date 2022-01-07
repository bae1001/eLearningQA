package es.ubu.lsi.model;

public class Tabledata{
    private Itemname itemname;
    private Leader leader;
    private GradeTableField weight;
    private GradeTableField grade;
    private GradeTableField range;
    private GradeTableField percentage;
    private GradeTableField feedback;
    private GradeTableField contributiontocoursetotal;

    public Tabledata() {}


    public Itemname getItemname() {
        return itemname;
    }

    public void setItemname(Itemname itemname) {
        this.itemname = itemname;
    }

    public Leader getLeader() {
        return leader;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }

    public GradeTableField getWeight() {
        return weight;
    }

    public void setWeight(GradeTableField weight) {
        this.weight = weight;
    }

    public GradeTableField getGrade() {
        return grade;
    }

    public void setGrade(GradeTableField grade) {
        this.grade = grade;
    }

    public GradeTableField getRange() {
        return range;
    }

    public void setRange(GradeTableField range) {
        this.range = range;
    }

    public GradeTableField getPercentage() {
        return percentage;
    }

    public void setPercentage(GradeTableField percentage) {
        this.percentage = percentage;
    }

    public GradeTableField getFeedback() {
        return feedback;
    }

    public void setFeedback(GradeTableField feedback) {
        this.feedback = feedback;
    }

    public GradeTableField getContributiontocoursetotal() {
        return contributiontocoursetotal;
    }

    public void setContributiontocoursetotal(GradeTableField contributiontocoursetotal) {
        this.contributiontocoursetotal = contributiontocoursetotal;
    }
}
