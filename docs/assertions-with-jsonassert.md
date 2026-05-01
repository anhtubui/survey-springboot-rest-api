# Assertions with JSONAssert

This phase focuses on writing robust and flexible assertions for JSON responses in integration tests.

## 13. Writing Assertions for JSON Responses

When testing REST APIs, we need to verify that the response body matches our expectations. Comparing raw JSON strings is difficult due to whitespace and formatting.

---

### The Problem with Raw String Comparison

Directly comparing strings like this is brittle:
```java
String expected = "{\"id\":\"Q1\",\"description\":\"Most Popular Cloud Platform\"}";
assertEquals(expected, response.getBody()); // Fails if there is an extra space!
```

---

### Introduction to JSONAssert

`JSONAssert` is a framework that allows for flexible JSON comparison. It ignores insignificant differences like whitespace and field order.

#### Strict vs. Non-Strict Mode
-   **Strict Mode (`true`)**: The JSONs must be identical. If the actual JSON has extra fields, the test fails.
-   **Non-Strict Mode (`false`)**: Allows the actual JSON to have extra fields. It only verifies that the specified fields match.

---

### Implementing Assertions in Integration Tests

**SurveyResourceIT.java**:
```java
@Test
void retrieveSpecificSurveyQuestion_basicScenario() throws JSONException {
    ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL, String.class);

    String expectedResponse = """
            {
                "id": "Q1",
                "description": "Most Popular Cloud Platform",
                "correctAnswer": "AWS"
            }
            """;

    // 1. Assert that the response status code is 200 OK
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    // 2. Verify that the response header contains the correct Content-Type (application/json)
    assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

    // 3. Use JSONAssert to perform a flexible comparison of the actual response body against our expected JSON.
    // The 'false' parameter enables non-strict mode, which ignores extra fields and formatting differences.
    JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
}
```

---

### Key Benefits

1.  **Readability**: Using Java Text Blocks (`"""`) makes the expected JSON easy to read and maintain.
2.  **Flexibility**: Non-strict mode allows you to check only the fields you care about, making tests resistant to changes in irrelevant fields.
3.  **Detailed Errors**: When an assertion fails, `JSONAssert` tells exactly which field was missing or different.
