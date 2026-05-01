# Testing Lists of Resources

This phase focuses on writing integration tests for endpoints that return collections of resources, such as a list of survey questions.

## 15. Testing Question Lists

When testing collections, we often want to verify that specific items exist in the list without necessarily checking every single detail of every item.

---

### Strategy for Testing Lists

1.  **Partial Verification**: Use `JSONAssert` in non-strict mode (`false`) to check for the presence of key items.
2.  **URL Constants**: Define separate constants for collection URLs to keep the test code clean.
3.  **HATEOAS and Metadata**: Be aware that automated APIs (like those from Spring Data REST) may wrap lists in metadata, requiring careful JSON path matching.

---

### Implementation

**SurveyResourceIT.java**:
```java
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

    // Verify that these specific IDs are present in the list
    JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
}
```

---

### Key Concepts

-   **Array Matching**: In non-strict mode, `JSONAssert` verifies that the objects in the expected array exist within the actual array. It does not require the arrays to be the same size or in the same order unless configured otherwise.
-   **Content-Type Consistency**: Collection endpoints should still return `application/json` (or a HAL-specific variation if using HATEOAS).
