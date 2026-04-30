package com.tutorial.rest_api.survey;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

@Service
public class SurveyService {

    private static List<Survey> surveys = new ArrayList<>();

    static {
        Question q1 = new Question("Q1", "Most popular Cloud Platform",
                Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle"), "AWS");
        Question q2 = new Question("Q2", "Fastest growing Language",
                Arrays.asList("Java", "Python", "JavaScript", "Swift"), "Python");

        List<Question> questions = new ArrayList<>(Arrays.asList(q1, q2));

        Survey survey = new Survey("Survey1", "Tech Survey", "A survey about modern technology", questions);

        surveys.add(survey);
    }

    public List<Survey> retrieveAllSurveys() {
        return surveys;
    }

    public Survey retrieveSurveyById(String surveyId) {
        Predicate<? super Survey> predicate = survey -> survey.getId().equalsIgnoreCase(surveyId);
        return surveys.stream().filter(predicate).findFirst().orElse(null);
    }

    public List<Question> retrieveAllSurveyQuestions(String surveyId) {
        Survey survey = retrieveSurveyById(surveyId);

        if (survey == null) {
            return null;
        }

        return survey.getQuestions();
    }

    public Question retrieveQuestionForSurvey(String surveyId, String questionId) {
        Survey survey = retrieveSurveyById(surveyId);

        if (survey == null) {
            return null;
        }

        List<Question> questions = survey.getQuestions();

        Predicate<? super Question> predicate = q -> q.getId().equalsIgnoreCase(questionId);
        return questions.stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }

    public String addNewSurveyQuestion(String surveyId, Question question) {
        List<Question> questions = retrieveAllSurveyQuestions(surveyId);
        question.setId(getRandomId());
        questions.add(question);

        return question.getId();
    }

    public String deleteSurveyQuestion(String surveyId, String questionId) {
        List<Question> questions = retrieveAllSurveyQuestions(surveyId);

        if (questions == null) {
            return null;
        }

        Predicate<? super Question> predicate = q -> q.getId().equalsIgnoreCase(questionId);
        boolean removed = questions.removeIf(predicate);

        if (!removed) {
            return null;
        }

        return questionId;
    }

    public void updateSurveyQuestion(String surveyId, String questionId, Question question) {
        List<Question> questions = retrieveAllSurveyQuestions(surveyId);

        Predicate<? super Question> predicate = q -> q.getId().equalsIgnoreCase(questionId);
        questions.removeIf(predicate);

        questions.add(question);
    }

    private String getRandomId() {

        SecureRandom secureRandom = new SecureRandom();
        String randomId = new BigInteger(32, secureRandom).toString();
        return randomId;
    }

}
