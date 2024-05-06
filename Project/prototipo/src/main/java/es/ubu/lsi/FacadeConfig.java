package es.ubu.lsi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.InputStream;
import java.util.Properties;

public class FacadeConfig {
    private static final Logger LOGGER = LogManager.getLogger();
    private int formatNumThreshold;
    private int excessiveNesting;
    private int forumResponseTime;
    private double minCommentPercentage;
    private int assignmentGradingTime;
    private double minFeedbackAnswerPercentage;
    private int forumRelevancePeriod;
    private int assignmentRelevancePeriod;
    private double facilityIndexMin;
    private double facilityIndexMax;
    private double minQuizEngagementPercentage;
    private double maxRandomScoreInQuizz;
    private String host;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public FacadeConfig(String filename) {
        if (filename.contains(".")) {
            filename = "Docencia_reglada";
        }
        try (InputStream stream = getClass().getClassLoader()
                .getResourceAsStream("configurations/" + filename)) {
            Properties properties = new Properties();
            properties.load(stream);
            formatNumThreshold = Integer.parseInt(properties.getProperty("format_num_threshold"));
            excessiveNesting = Integer.parseInt(properties.getProperty("excessive_nesting"));
            forumResponseTime = Integer.parseInt(properties.getProperty("forum_response_time"));
            minCommentPercentage = Double.parseDouble(properties.getProperty("min_comment_percentage"));
            assignmentGradingTime = Integer.parseInt(properties.getProperty("assignment_grading_time"));
            minFeedbackAnswerPercentage = Double.parseDouble(properties.getProperty("min_feedback_answer_percentage"));
            forumRelevancePeriod = Integer.parseInt(properties.getProperty("forum_relevance_period"));
            assignmentRelevancePeriod = Integer.parseInt(properties.getProperty("assignment_relevance_period"));
            facilityIndexMin = Double.parseDouble(properties.getProperty("facility_index_min"));
            facilityIndexMax = Double.parseDouble(properties.getProperty("facility_index_max"));
            minQuizEngagementPercentage = Double.parseDouble(properties.getProperty("min_quiz_engagement_percentage"));
            maxRandomScoreInQuizz = Double.parseDouble(properties.getProperty("max_random_guess_score"));

        } catch (Exception e) {
            LOGGER.error("exception", e);
        }
    }

    public double getMaxRandomScoreInQuizz() {
        return maxRandomScoreInQuizz;
    }

    public double getMinQuizEngagementPercentage() {
        return minQuizEngagementPercentage;
    }

    public int getFormatNumThreshold() {
        return formatNumThreshold;
    }

    public int getExcessiveNesting() {
        return excessiveNesting;
    }

    public int getForumResponseTime() {
        return forumResponseTime;
    }

    public double getMinCommentPercentage() {
        return minCommentPercentage;
    }

    public double getFacilityIndexMin() {
        return facilityIndexMin;
    }

    public double getFacilityIndexMax() {
        return facilityIndexMax;
    }

    public int getAssignmentGradingTime() {
        return assignmentGradingTime;
    }

    public double getMinFeedbackAnswerPercentage() {
        return minFeedbackAnswerPercentage;
    }

    public int getForumRelevancePeriod() {
        return forumRelevancePeriod;
    }

    public int getAssignmentRelevancePeriod() {
        return assignmentRelevancePeriod;
    }
}
