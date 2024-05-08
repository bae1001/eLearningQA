package es.ubu.lsi.model;

public class Question {
    private int questionNumber;
    private String questionName;
    private int quizId;
    private double discriminationIndex;
    private double randomGuessScore;
    private double facilityIndex;

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public double getDiscriminationIndex() {
        return discriminationIndex;
    }

    public void setDiscriminationIndex(double discriminationIndex) {
        this.discriminationIndex = discriminationIndex;
    }

    public double getRandomGuessScore() {
        return randomGuessScore;
    }

    public void setRandomGuessScore(double randomGuessScore) {
        this.randomGuessScore = randomGuessScore;
    }

    public double getFacilityIndex() {
        return facilityIndex;
    }

    public void setFacilityIndex(double facilityIndex) {
        this.facilityIndex = facilityIndex;
    }
}
