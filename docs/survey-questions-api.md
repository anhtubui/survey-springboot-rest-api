# Questionnaire REST API: Survey Questions

This phase focuses on building a hierarchical API structure to manage questions within specific surveys.

## 5. Building Survey Questions APIs

The goal is to provide deep access to resources by following a nested URL pattern: `/surveys/{surveyId}/questions`.

### Retrieving All Questions for a Survey
The method captures the `surveyId` and returns the list of questions associated with that survey.

**Controller Implementation**:
```java
@GetMapping("/surveys/{surveyId}/questions")
public List<Question> retrieveQuestionsForSurvey(@PathVariable String surveyId) {
    List<Question> questions = surveyService.retrieveAllSurveyQuestions(surveyId);

    if (questions == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    return questions;
}
```

**Service Logic**:
The service first validates if the survey exists. If not, it returns `null` to trigger the 404 in the controller.
```java
public List<Question> retrieveAllSurveyQuestions(String surveyId) {
    Survey survey = retrieveSurveyById(surveyId);
    if (survey == null) return null;
    return survey.getQuestions();
}
```

---

### Retrieving a Specific Question by ID
This endpoint requires two path variables: `surveyId` and `questionId`. It filters the questions of the specified survey to find the exact match.

**URL Structure**: `/surveys/{surveyId}/questions/{questionId}`

**Controller Implementation**:
```java
@GetMapping("/surveys/{surveyId}/questions/{questionId}")
public Question retrieveQuestionForSurvey(@PathVariable String surveyId, @PathVariable String questionId) {
    Question question = surveyService.retrieveQuestionForSurvey(surveyId, questionId);

    if (question == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    return question;
}
```

**Service Logic**:
Uses Java Streams to filter the list of questions.
```java
public Question retrieveQuestionForSurvey(String surveyId, String questionId) {
    Survey survey = retrieveSurveyById(surveyId);
    if (survey == null) return null;

    Predicate<? super Question> predicate = q -> q.getId().equalsIgnoreCase(questionId);
    return survey.getQuestions().stream()
            .filter(predicate)
            .findFirst()
            .orElse(null);
}
```

---

### Creating a New Survey Question (POST)
The `POST` method is used to add a new question to an existing survey. This demonstrates how to capture a request body and modify in-memory data.

**URL Pattern**: `/surveys/{surveyId}/questions`

**Controller Implementation**:
```java
@PostMapping("/surveys/{surveyId}/questions")
public void addNewSurveyQuestion(@PathVariable String surveyId, @RequestBody Question question) {
    surveyService.addNewSurveyQuestion(surveyId, question);
}
```

**Service Logic**:
The service retrieves the survey's question list and adds the new question object to it.
```java
public void addNewSurveyQuestion(String surveyId, Question question) {
    List<Question> questions = retrieveAllSurveyQuestions(surveyId);
    questions.add(question);
}
```

### Testing with Talend API Tester
To verify the `POST` request:
1.  **Method**: `POST`
2.  **URL**: `http://localhost:8080/surveys/Survey1/questions`
3.  **Body**: (Set Type to `JSON`)
    ```json
    {
      "id": "Q3",
      "description": "Most popular JS Framework",
      "options": ["React", "Angular", "Vue", "Svelte"],
      "correctAnswer": "React"
    }
    ```
4.  **Verification**: Send a `GET` request to `/surveys/Survey1/questions` to see the newly added question.

---


1.  **Hierarchy**: Use sub-resources to show ownership. A question belongs to a survey, so the path is `/surveys/{id}/questions`.
2.  **Plural Naming**: Use `/surveys` and `/questions` to indicate collections.
3.  **Explicit Mapping**: Use `@PathVariable` names that match the URI placeholders for clarity.
