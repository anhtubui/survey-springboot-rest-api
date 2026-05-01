package com.tutorial.rest_api.survey;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {

    // @Autowired tells Spring to automatically inject the TestRestTemplate bean
    // into this field.
    // Since we are using @SpringBootTest, Spring Boot pre-configures this bean to
    // be ready
    // to make requests to the server running on a random port.
    @Autowired
    private TestRestTemplate template;

    private static String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Q1";
    private static String GENERIC_QUESTIONS_URL = "/surveys/Survey1/questions";

    @Test
    void retrieveAllSurveyQuestions_basicScenario() throws JSONException {
        ResponseEntity<String> responseEntity = template.getForEntity(GENERIC_QUESTIONS_URL, String.class);

        String expectedResponse = """
                [
                    {"id": "Q1"},
                    {"id": "Q2"}
                ]
                """;

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
    }

    @Test
    void retrieveSpecificSurveyQuestion_basicScenario() throws JSONException {
        ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL, String.class);

        String expectedResponse = """
                {
                    "id": "Q1",
                    "description": "Most popular Cloud Platform",
                    "correctAnswer": "AWS"
                }
                """;

        // Assert that the response status code is successful (2xx)
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        // Verify that the response header contains the correct Content-Type
        // (application/json)
        String contentType = responseEntity.getHeaders().get("Content-Type").get(0);
        assertTrue(contentType.contains("application/json"));

        // Use JSONAssert to perform a flexible comparison of the actual response body
        // against our expected JSON.
        // The 'false' parameter enables non-strict mode, which ignores extra fields and
        // formatting differences.
        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
    }
}
