# REST API: ID Generation and Best Practices

This phase enhances the resource creation process by automating ID generation and adhering to advanced REST standards for responses.

## 6. ID Generation and Response Status Improvements

### Secure Random ID Generation
Instead of client-provided IDs, the server now generates cryptographically strong random IDs using `SecureRandom`.

**Service Implementation**:
```java
public String addNewSurveyQuestion(String surveyId, Question question) {
    List<Question> questions = retrieveAllSurveyQuestions(surveyId);
    question.setId(getRandomId()); // Automated ID assignment
    questions.add(question);
    return question.getId();
}

private String getRandomId() {
    SecureRandom secureRandom = new SecureRandom();
    return new BigInteger(32, secureRandom).toString();
}
```

---

### ResponseEntity and 201 Created Status
Using `ResponseEntity` allows us to customize the HTTP response beyond just the body. When a resource is created, we return `201 Created`.

### Dynamic Location Header
Following REST best practices, the response includes a `Location` header pointing to the newly created resource. This is built dynamically using `ServletUriComponentsBuilder`.

**Controller Implementation**:
```java
@PostMapping("/surveys/{surveyId}/questions")
public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable String surveyId, @RequestBody Question question) {
    String newQuestionId = surveyService.addNewSurveyQuestion(surveyId, question);

    // Build URI for the new resource: /surveys/{surveyId}/questions/{newQuestionId}
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(newQuestionId)
            .toUri();

    return ResponseEntity.created(location).build();
}
```

---

## Key Benefits
1.  **Automation**: Clients don't need to worry about unique ID collisions.
2.  **Informativeness**: The `201 Created` status explicitly confirms success.
3.  **Discoverability**: The `Location` header tells the client exactly where to find the new resource without them having to guess the URL.
