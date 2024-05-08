package es.ubu.lsi.model;

import java.util.List;

public class Quiz {
    private String id;
    private String coursemodule;
    private String course;
    private String timeclose;
    private String name;
    private boolean visible;
    private double quizEngagement;
    private double quizFacilityIndex;
    private double quizRandomGuessScore;
    private List<Question> questions;
    private List<AttemptsList> quizAttempts;

    public double getQuizFacilityIndex() {
        return quizFacilityIndex;
    }

    public void setQuizFacilityIndex() {
        double sumOfQuizFacilityIndex = 0;
        double countedQuestion = 0;

        for (Question question : questions) {
            sumOfQuizFacilityIndex += (question.getFacilityIndex() / 100);
            countedQuestion++;
        }

        if (countedQuestion == 0) {
            quizFacilityIndex = 0;
        }

        quizFacilityIndex = sumOfQuizFacilityIndex / countedQuestion;
    }

    public double getQuizEngagement() {
        return quizEngagement;
    }

    public void setQuizEngagement(double quizEngagement) {
        this.quizEngagement = quizEngagement;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoursemodule() {
        return coursemodule;
    }

    public void setCoursemodule(String coursemodule) {
        this.coursemodule = coursemodule;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTimeclose() {
        return timeclose;
    }

    public void setTimeclose(String timeclose) {
        this.timeclose = timeclose;
    }

    public List<AttemptsList> getQuizAttempts() {
        return quizAttempts;
    }

    public void setQuizAttempts(List<AttemptsList> quizAttempts) {
        this.quizAttempts = quizAttempts;
    }

    public double getQuizRandomGuessScore() {
        return quizRandomGuessScore;
    }

    public void setQuizRandomGuessScore() {
        for (Question question : questions) {
            quizRandomGuessScore += question.getRandomGuessScore();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
