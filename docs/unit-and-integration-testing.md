# Unit and Integration Testing

This phase focuses on ensuring the reliability and correctness of our REST API through automated testing.

## 12. Integration Testing with @SpringBootTest

We use integration tests to verify that all layers of the application (Resource, Service, and Data) work together correctly.

---

### Unit Tests vs. Integration Tests

- **Unit Tests**: Target a specific unit of code (e.g., a single method in a Service) in isolation. Dependencies are usually mocked.
- **Integration Tests**: Launch the **full application context** and test the application as a whole. This ensures that the wiring and configuration (like component scanning and dependency injection) are correct.

---

### Creating an Integration Test

We use the `@SpringBootTest` annotation with a random port to avoid conflicts with other running applications.

**SurveyResourceIT.java**:

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {

    @Autowired
    private TestRestTemplate template;

    private static String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Q1";

    @Test
    void retrieveSpecificSurveyQuestion_basicScenario() {
        // Send a GET request to the endpoint
        ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL, String.class);

        // Verify the response
        System.out.println(responseEntity.getBody());
        System.out.println(responseEntity.getHeaders());
    }
}
```

---

### Key Components

1.  **@SpringBootTest**: Annotates the test class to launch the full Spring application context.
2.  **webEnvironment = RANDOM_PORT**: Tells Spring Boot to start the embedded server on a random available port.
3.  **TestRestTemplate**: A convenient class provided by Spring Boot to perform HTTP requests during tests. It is automatically configured to use the random port assigned to the application.
4.  **JSON Response Handling**: In the initial phase, we log the response body and headers to verify that the endpoint is reachable and returning data.

---

### Running Tests

You can run the tests using your IDE's test runner or via Maven:

```bash
./mvnw test
```

> [!NOTE]
> Integration tests usually take longer than unit tests because they need to bootstrap the entire Spring framework and start an embedded web server.
