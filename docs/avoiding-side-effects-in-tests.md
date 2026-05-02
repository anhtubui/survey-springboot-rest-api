# Avoiding Side Effects in Tests

This phase focuses on ensuring that tests are isolated and do not leave behind persistent changes that could affect subsequent test runs.

## 17. Managing State and Side Effects

Tests should be **idempotent**, meaning they leave the system in the same state as they found it. This is especially important for integration tests that interact with a shared database.

---

### The Problem: Shared State

If one test adds a resource (like a new survey question) and another test counts the total number of questions, the order in which they run becomes critical. Since JUnit does not guarantee test execution order, this leads to flaky tests.

---

### The Solution: Cleanup

A common pattern for "Create" tests is to immediately delete the resource at the end of the test.

**SurveyResourceIT.java**:
```java
@Test
void addNewSurveyQuestion_basicScenario() {

    // ... set up and send POST request ...

    // 4. Assertions
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    // Verify and capture Location header
    String locationHeader = responseEntity.getHeaders().get("Location").get(0);
    assertTrue(locationHeader.contains("/surveys/Survey1/questions/"));

    // 5. Cleanup (Avoid Side Effects)
    // Perform a DELETE request to remove the newly created resource
    template.delete(locationHeader);
}
```

---

### Best Practices

1.  **Cleanup in the Test**: For simple cases, deleting at the end of the `@Test` method is effective.
2.  **Use `@AfterEach`**: For more complex setups, use a cleanup method annotated with `@AfterEach` to ensure it runs even if an assertion fails.
3.  **Database Reset**: In larger projects, you might use `@DirtiesContext` or transaction-based testing (`@Transactional`) to roll back changes automatically.
4.  **Independent Tests**: Always assume your tests will run in a random order. Never write a test that depends on the side effect of a previous test.
