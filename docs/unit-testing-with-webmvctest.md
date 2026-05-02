# Unit Testing with @WebMvcTest

This phase focuses on testing individual layers of the application in isolation by mocking their dependencies.

## 18. Testing the Web Layer with Mocking

Unlike integration tests that launch the entire application, web layer unit tests focus only on the REST controllers and their interactions with the business layer.

---

### Key Annotations

-   **@WebMvcTest**: Used to test a specific controller. It only bootstraps the web layer (Spring MVC), making the tests much faster than `@SpringBootTest`.
-   **@MockBean**: Used to add mocks to the Spring Application Context. The mock will replace any existing bean of the same type in the context.
-   **MockMvc**: A tool to simulate HTTP requests to the controller without running a real server.

---

### Implementation

**SurveyResourceTest.java**:
```java
@WebMvcTest(controllers = SurveyResource.class)
public class SurveyResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SurveyService surveyService;

    private static String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Q1";

    @Test
    void retrieveSpecificSurveyQuestion_404Scenario() throws Exception {
        RequestBuilder requestBuilder = 
                MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        // Initially, surveyService.retrieveQuestionForSurvey() returns null
        // because it's a mock. This should result in a 404 Not Found.
        assertEquals(404, mvcResult.getResponse().getStatus());
    }
}
```

---

### Why Mocking?

Mocking allows us to:
1.  **Isolate the Unit**: We only test the logic inside `SurveyResource` (e.g., how it handles a `null` return value from the service).
2.  **Speed Up Tests**: We don't need to initialize the database or complex service logic.
3.  **Simulate Scenarios**: We can easily simulate error conditions, timeouts, or specific data returns that might be hard to trigger with a real database.
