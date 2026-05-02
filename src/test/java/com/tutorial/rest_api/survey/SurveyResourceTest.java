package com.tutorial.rest_api.survey;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

// Reference: docs/unit-testing-with-webmvctest.md
@WebMvcTest(controllers = SurveyResource.class)
public class SurveyResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SurveyService surveyService;

    private static String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Q1";

    @Test
    void retrieveSpecificSurveyQuestion_404Scenario() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    // Reference: docs/mocking-service-methods-in-unit-tests.md
    @Test
    void retrieveSpecificSurveyQuestion_basicScenario() throws Exception {

        Question question = new Question("Q1", "Most popular Cloud Platform",
                Arrays.asList("AWS", "Azure", "GCP", "Oracle"), "AWS");

        // Stubbing the service call
        when(surveyService.retrieveQuestionForSurvey("Survey1", "Q1")).thenReturn(question);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expectedResponse = """
                {
                    "id": "Q1",
                    "description": "Most popular Cloud Platform",
                    "correctAnswer": "AWS"
                }
                """;

        // Assertions
        assertEquals(200, mvcResult.getResponse().getStatus());
        JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), false);
    }
}
