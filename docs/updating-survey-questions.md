# REST API: Updating Survey Questions

This document details the implementation of the `PUT` method to update a specific question within a survey.

## 8. Implementing the PUT Method

The `PUT` method is used for a full replacement of a resource. In this implementation, we replace an existing survey question with updated details provided in the request body.

### Resource Replacement Pattern
The endpoint targets a specific question using hierarchical path variables, similar to `GET` and `DELETE`.

**Controller Implementation**:
```java
@PutMapping("/surveys/{surveyId}/questions/{questionId}")
public ResponseEntity<Object> updateSurveyQuestion(@PathVariable String surveyId, @PathVariable String questionId,
        @RequestBody Question question) {
    surveyService.updateSurveyQuestion(surveyId, questionId, question);
    return ResponseEntity.noContent().build();
}
```

---

### Service Logic for Question Update
The service logic follows a "remove-then-add" strategy to ensure the question is updated within the survey's list.

**Service Implementation**:
```java
public void updateSurveyQuestion(String surveyId, String questionId, Question question) {
    List<Question> questions = retrieveAllSurveyQuestions(surveyId);
    
    // Remove the old version
    Predicate<? super Question> predicate = q -> q.getId().equalsIgnoreCase(questionId);
    questions.removeIf(predicate);
    
    // Add the updated version
    questions.add(question);
}
```

---

### Testing the Update API
To verify the update using Talend API Tester or a similar tool:
1.  **Initial State**: Send a `GET` request to `/surveys/Survey1/questions/Q1` to see current details.
2.  **Update**: Send a `PUT` request to `/surveys/Survey1/questions/Q1`.
    - **Body**: (Modified JSON)
      ```json
      {
        "id": "Q1",
        "description": "Updated Description",
        "options": ["Opt1", "Opt2"],
        "correctAnswer": "Opt1"
      }
      ```
3.  **Expected Response**: `204 No Content`.
4.  **Verification**: Send another `GET` request to `/surveys/Survey1/questions/Q1` to confirm the fields have changed.
