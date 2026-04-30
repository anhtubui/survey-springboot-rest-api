# Questionnaire REST API: Retrieving a Specific Survey

## 5. Building the REST API for Specific Survey Retrieval

We are expanding the API to allow fetching a single survey by its ID. This involves capturing dynamic values from the URL and implementing the lookup logic.

### Captured URL Path Variables
Use the `@PathVariable` annotation to capture the `surveyId` from the URL path. In the controller, we map the `{surveyId}` placeholder to the method parameter.

```java
@GetMapping("/surveys/{surveyId}")
public Survey retrieveSurveyById(@PathVariable String surveyId) {
    Survey survey = surveyService.retrieveSurveyById(surveyId);

    if (survey == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    return survey;
}
```

### Implementing Survey Lookup Logic
The service layer handles the search using Java Streams. This approach is cleaner and more functional than traditional loops.

```java
public Survey retrieveSurveyById(String surveyId) {
    return surveys.stream()
            .filter(survey -> survey.getId().equalsIgnoreCase(surveyId))
            .findFirst()
            .orElse(null);
}
```

### Key Logic Steps:
1.  **Stream**: Convert the list of surveys into a stream.
2.  **Filter**: Use a **predicate** with `.equalsIgnoreCase()` to find a match.
3.  **Optional Handling**: `findFirst()` returns an `Optional`. We use `.orElse(null)` to handle the case where no match is found, which the controller then uses to throw a 404.

### Handling Response Status
- **Error Handling**: If the service returns `null`, the controller throws a `ResponseStatusException(HttpStatus.NOT_FOUND)`, ensuring the client receives a proper `404` status code.
- **Success**: If found, the `Survey` object is returned, and Spring automatically serializes it into JSON with an `HTTP 200 OK` status.

---

## 6. Testing the API Endpoint

Once implemented, verify the following scenarios:

1.  **Existing ID**: 
    - URL: `GET http://localhost:8080/surveys/Survey1`
    - Expected: JSON for the "Tech Survey" and `200 OK`.

2.  **Non-existing ID**: 
    - URL: `GET http://localhost:8080/surveys/InvalidID`
    - Expected: `404 Not Found` status.

