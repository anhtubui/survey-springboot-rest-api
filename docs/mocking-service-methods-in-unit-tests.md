# Mocking Service Methods in Unit Tests

This phase focuses on stubbing (mocking) specific service methods to return data and verifying the controller's behavior using MockMvc.

## 19. Stubbing with Mockito and Asserting JSON

When unit testing a controller, we use Mockito to tell the mocked service exactly what to return for a specific method call.

---

### Stubbing with `when().thenReturn()`

We use the static `when` method from Mockito to define the behavior of our mock.

```java
Question question = new Question("Q1", "Most Popular Cloud Platform", "AWS", Arrays.asList("AWS", "Azure", "GCP", "Oracle"));

when(surveyService.retrieveQuestionForSurvey("Survey1", "Q1")).thenReturn(question);
```

---

### Implementation

**SurveyResourceTest.java**:
```java
@Test
void retrieveSpecificSurveyQuestion_basicScenario() throws Exception {

    Question question = new Question("Q1", "Most popular Cloud Platform", "AWS",
            Arrays.asList("AWS", "Azure", "GCP", "Oracle"));

    // Stubbing the service call
    when(surveyService.retrieveQuestionForSurvey("Survey1", "Q1")).thenReturn(question);

    RequestBuilder requestBuilder = 
            MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);

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
```

---

### Key Concepts

1.  **Mockito Stubbing**: `when(...).thenReturn(...)` allows you to control the data flow between layers without executing the actual business logic.
2.  **MockMvcResult**: The `mvcResult` object provides access to the full HTTP response, including the status code, headers, and body.
3.  **JSONAssert in Unit Tests**: Just like in integration tests, `JSONAssert` ensures that the JSON returned by the controller matches our expected structure, regardless of formatting.
4.  **Isolation**: By mocking the service, we ensure the test only fails if there is a bug in the `SurveyResource` code (e.g., wrong URL mapping or incorrect response status handling).
