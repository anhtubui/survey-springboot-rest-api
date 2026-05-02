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

### Why not postForEntity()?

In Spring's `TestRestTemplate`, `template.exchange()` is the most flexible and powerful method for making HTTP calls. Here is why it's being used at line 51 instead of a simpler method like `postForEntity`:

### 1. Full Control over Headers

The primary reason is that `exchange` allows you to pass an **`HttpEntity`** (line 48). An `HttpEntity` wraps both the **Request Body** and **HTTP Headers**.

- In this code, you are manually setting the `Content-Type` to `application/json` (lines 44-45).
- Convenience methods like `postForEntity` are simpler but don't give you this direct control over headers as easily as `exchange` does.

### 2. Universal Method

`exchange` can handle **any** HTTP method (GET, POST, PUT, DELETE, PATCH, etc.).

- By using `exchange`, you use a consistent pattern across your tests.
- If you later decide to change this from a `POST` to a `PUT`, you only have to change the `HttpMethod.POST` argument, rather than switching to a completely different method name.

### 3. Access to the full ResponseEntity

While other methods also return a `ResponseEntity`, `exchange` is often the "go-to" in integration tests because it explicitly shows the intent: _"I am exchanging this Request (Entity) for that Response (Class)."_

**In short:** You use `template.exchange` when you need to send **custom headers** along with your request body and want full control over the interaction.

---

### Key Concepts

- **template.exchange()**: The most flexible method in `TestRestTemplate`. It is preferred over `postForEntity()` because:
  - **Custom Headers**: It allows passing an `HttpEntity` which can contain both the request body and custom headers (like `Content-Type: application/json`).
  - **Generic Nature**: It can handle any HTTP method (GET, POST, PUT, DELETE, etc.), making it a consistent choice for complex requests.
- **HttpMethod.POST**: Explicitly specifies the HTTP method when using the `exchange` method.
- **201 Created**: The standard successful response status for resource creation.
- **Location Header**: Contains the URI of the newly created resource, allowing the client to retrieve it immediately.
- **Test Data Management**: Since POST requests modify the application state, be mindful of test order. If a subsequent GET test relies on a specific number of items, a preceding POST test might cause it to fail.
