# Refining and Enhancing Integration Tests

This phase focuses on improving the structure, readability, and robustness of our integration tests.

## 14. Best Practices for Integration Testing

A well-structured test ensures that failures are easy to debug and that the test itself is maintainable as the API evolves.

---

### Structured Assertion Flow

To ensure clear failure messages, assertions should follow a logical sequence:

1.  **Response Status**: First, verify the request was successful (e.g., 2xx status code).
2.  **Content-Type**: Verify the response format is correct (e.g., `application/json`).
3.  **JSON Content**: Finally, verify the actual data using `JSONAssert`.

---

### Enhanced Assertions

Instead of hardcoding a specific status code like `200 OK`, we can use `is2xxSuccessful()` to allow for a broader range of successful responses if appropriate.

**SurveyResourceIT.java**:
```java
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

    // 1. Assert Status Code (Using more descriptive check)
    assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

    // 2. Assert Content-Type (Considering potential multiple values or charsets)
    String contentType = responseEntity.getHeaders().get("Content-Type").get(0);
    assertTrue(contentType.contains("application/json"));

    // 3. Assert Response Body with JSONAssert
    JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
}
```

---

### Test Cleanup Checklist

-   **Constants**: Move URLs and expected JSON templates to constants or text blocks for reuse.
-   **No Placeholders**: Remove unnecessary `System.out.println` statements once assertions are in place.
-   **Reuse Template**: Ensure `TestRestTemplate` is correctly injected and reused across all test methods.
-   **Logical Naming**: Name test methods clearly to reflect the scenario (e.g., `_basicScenario`, `_notFoundScenario`).
