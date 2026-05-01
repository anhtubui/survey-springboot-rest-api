package com.tutorial.rest_api.survey;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {

    @Autowired
    private TestRestTemplate template;

    private static String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Q1";

    @Test
    void retrieveSpecificSurveyQuestion_basicScenario() {
        ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL, String.class);

        System.out.println(responseEntity.getBody());
        System.out.println(responseEntity.getHeaders());
    }
}
