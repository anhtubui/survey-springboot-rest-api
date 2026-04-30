package com.tutorial.rest_api.survey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
