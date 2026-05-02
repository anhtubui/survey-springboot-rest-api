# Integration Testing the POST Method

This phase focuses on testing the creation of new resources by sending POST requests with JSON bodies and verifying the response metadata.

## 16. Testing Resource Creation (POST)

Testing a POST request involves more complexity than a GET request because it requires sending a request body and setting appropriate headers.

---

### Request Setup with HttpEntity

To send a POST request with headers and a body, we use `HttpHeaders` and `HttpEntity`.

1.  **HttpHeaders**: Used to set the `Content-Type` to `application/json`.
2.  **HttpEntity**: Wraps both the JSON body (as a string or object) and the headers.

---

### Implementation

**SurveyResourceIT.java**:
```java
@Test
void addNewSurveyQuestion_basicScenario() {

    String requestBody = """
            {
                "description": "Your Favorite Language",
                "options": ["Java", "Python", "JavaScript", "Rust"],
                "correctAnswer": "Java"
            }
            """;

    // 1. Set Headers
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");

    // 2. Create HttpEntity
    HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);

    // 3. Send POST Request
    ResponseEntity<String> responseEntity 
        = template.exchange(GENERIC_QUESTIONS_URL, HttpMethod.POST, httpEntity, String.class);

    // 4. Assertions
    // Verify 201 Created status
    assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    // Verify Location header
    String location = responseEntity.getHeaders().get("Location").get(0);
    assertTrue(location.contains("/surveys/Survey1/questions/"));
}
```

---

### Key Concepts

-   **HttpMethod.POST**: Explicitly specifies the HTTP method when using the `exchange` method.
-   **201 Created**: The standard successful response status for resource creation.
-   **Location Header**: Contains the URI of the newly created resource, allowing the client to retrieve it immediately.
-   **Test Data Management**: Since POST requests modify the application state, be mindful of test order. If a subsequent GET test relies on a specific number of items, a preceding POST test might cause it to fail.
