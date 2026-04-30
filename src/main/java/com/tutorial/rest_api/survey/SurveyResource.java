package com.tutorial.rest_api.survey;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class SurveyResource {

    private final SurveyService surveyService;

    public SurveyResource(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping("/surveys")
    public List<Survey> retrieveAllSurveys() {
        return surveyService.retrieveAllSurveys();
    }

    @GetMapping("/surveys/{surveyId}")
    public Survey retrieveSurveyById(@PathVariable String surveyId) {
        Survey survey = surveyService.retrieveSurveyById(surveyId);

        if (survey == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return survey;
    }

    @GetMapping("/surveys/{surveyId}/questions")
    public List<Question> retrieveQuestionsForSurvey(@PathVariable String surveyId) {
        List<Question> questions = surveyService.retrieveAllSurveyQuestions(surveyId);

        if (questions == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return questions;
    }

    @GetMapping("/surveys/{surveyId}/questions/{questionId}")
    public Question retrieveQuestionForSurvey(@PathVariable String surveyId, @PathVariable String questionId) {
        Question question = surveyService.retrieveQuestionForSurvey(surveyId, questionId);

        if (question == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return question;
    }

    @PostMapping("/surveys/{surveyId}/questions")
    public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable String surveyId, @RequestBody Question question) {
        String newQuestionId = surveyService.addNewSurveyQuestion(surveyId, question);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newQuestionId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/surveys/{surveyId}/questions/{questionId}")
    public ResponseEntity<Object> deleteSurveyQuestion(@PathVariable String surveyId,
            @PathVariable String questionId) {
        surveyService.deleteSurveyQuestion(surveyId, questionId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/surveys/{surveyId}/questions/{questionId}")
    public ResponseEntity<Object> updateSurveyQuestion(@PathVariable String surveyId, @PathVariable String questionId,
            @RequestBody Question question) {
        surveyService.updateSurveyQuestion(surveyId, questionId, question);
        return ResponseEntity.noContent().build();
    }

}


