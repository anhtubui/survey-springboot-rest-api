# REST API: Deleting Survey Questions

This document details the implementation of the `DELETE` method to remove a specific question from a survey.

## 7. Implementing the DELETE Method

To remove a specific resource, we use the `DELETE` HTTP method. The URL structure follows the same hierarchical pattern used for `GET` requests.

### Hierarchical Delete Pattern
The endpoint targets a specific question within a survey using both `surveyId` and `questionId`.

**Controller Implementation**:
```java
@DeleteMapping("/surveys/{surveyId}/questions/{questionId}")
public ResponseEntity<Object> deleteSurveyQuestion(@PathVariable String surveyId, @PathVariable String questionId) {
    surveyService.deleteSurveyQuestion(surveyId, questionId);
    return ResponseEntity.noContent().build();
}
```

---

### Service Logic for Resource Removal
The service identifies the correct survey, retrieves its questions, and removes the one matching the `questionId`.

**Service Implementation**:
```java
public String deleteSurveyQuestion(String surveyId, String questionId) {
    List<Question> questions = retrieveAllSurveyQuestions(surveyId);

    if (questions == null) return null;

    Predicate<? super Question> predicate = q -> q.getId().equalsIgnoreCase(questionId);
    boolean removed = questions.removeIf(predicate);

    if (!removed) return null;

    return questionId;
}
```

---

### 204 No Content Status
Upon successful deletion, it is standard practice to return an `HTTP 204 No Content` status. This informs the client that the action was successful and that there is no further information to return in the response body.

## Testing the DELETE Method
To verify the implementation using a tool like Talend API Tester:
1.  **Method**: `DELETE`
2.  **URL**: `http://localhost:8080/surveys/Survey1/questions/Q1`
3.  **Expected Response**: `204 No Content`.
4.  **Verification**: Send a `GET` request to `/surveys/Survey1/questions` to confirm the question is no longer in the list.
