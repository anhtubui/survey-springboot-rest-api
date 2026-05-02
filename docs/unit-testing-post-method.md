# Unit Testing the POST Method

This phase focuses on unit testing the creation of resources by simulating POST requests with JSON bodies and stubbing the service layer to return a success indicator.

## 20. Mocking POST Requests with MockMvc

Testing a POST method in a unit test requires sending a body, specifying the content type, and verifying headers like `Location`.

---

### Request Building with `MockMvcRequestBuilders.post()`

To send a POST request, we use `MockMvcRequestBuilders.post()` and chain it with `.content()` and `.contentType()`.

```java
RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post(GENERIC_QUESTIONS_URL)
        .accept(MediaType.APPLICATION_JSON)
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON);
```

---

### Implementation

**SurveyResourceTest.java**:
```java
@Test
void addNewSurveyQuestion_basicScenario() throws Exception {

    String requestBody = """
            {
                "description": "Your Favorite Language",
                "options": ["Java", "Python", "JavaScript", "Rust"],
                "correctAnswer": "Java"
            }
            """;

    // Stub the service to return a dummy ID
    when(surveyService.addNewSurveyQuestion(anyString(), any(Question.class)))
            .thenReturn("SOME_ID");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
            .post(GENERIC_QUESTIONS_URL)
            .accept(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON);

    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

    MockHttpServletResponse response = mvcResult.getResponse();

    // Verify 201 Created
    assertEquals(201, response.getStatus());

    // Verify Location header contains the mocked ID
    String location = response.getHeader("Location");
    assertTrue(location.contains("/surveys/Survey1/questions/SOME_ID"));
}
```

---

### Key Concepts

1.  **Stubbing with `any()`**: Since we are sending a JSON string that Spring deserializes into a `Question` object, we use Mockito's `any(Question.class)` matcher to stub the call regardless of the exact object instance created.
2.  **Request Body**: The `content()` method takes the raw JSON string as input.
3.  **MockHttpServletResponse**: Provides access to headers (like `Location`) and status codes in a way that is easy to assert.
4.  **No Side Effects**: Unlike integration tests, this unit test does **not** actually add anything to a database. The service is mocked, so we don't need a cleanup phase.
