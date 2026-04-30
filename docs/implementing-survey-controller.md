# Questionnaire REST API: Development Progress

## 1. The Survey Resource Controller

To handle incoming HTTP requests, we implement a `RestController`. This class acts as the entry point for the API, mapping specific URLs to Java methods.

### Implementation of `SurveyResource`

The controller uses constructor injection to access the `SurveyService`, ensuring a clean, testable architecture.

```java
@RestController
public class SurveyResource {

    private final SurveyService surveyService;

    // Constructor Injection (Recommended)
    public SurveyResource(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping("/surveys")
    public List<Survey> retrieveAllSurveys() {
        return surveyService.retrieveAllSurveys();
    }
}
```

---

## 2. Connecting the Layers

The flow of data follows the standard Spring Boot Web MVC pattern:

1.  **Request**: The client sends a `GET` request to `/surveys`.
2.  **Controller**: `SurveyResource` receives the request and calls the `retrieveAllSurveys()` method in the Service.
3.  **Service**: `SurveyService` returns the static list of survey data.
4.  **Serialization**: Spring Boot uses the **Jackson** library to automatically convert the Java List into a **JSON** format.
5.  **Response**: The client receives a JSON array containing all survey details.

---

## 3. Testing the Endpoint

Once the application is running, you can verify the implementation by navigating to:
`http://localhost:8080/surveys`

**Expected JSON Output:**

```json
[
  {
    "id": "Survey1",
    "title": "Tech Survey",
    "description": "A survey about modern technology",
    "questions": [
      {
        "id": "Q1",
        "description": "Most popular Cloud Platform",
        "options": ["AWS", "Azure", "Google Cloud", "Oracle"],
        "correctAnswer": "AWS"
      }
    ]
  }
]
```

---

## 4. Key Concepts Applied

- **@RestController**: Combines `@Controller` and `@ResponseBody`, ensuring that data returned from methods is written directly into the HTTP response body.
- **Plural Naming**: Following RESTful best practices by using `/surveys` instead of `/getSurveys` or `/survey`.
- **Constructor Injection**: Promotes immutability and ensures the controller cannot be instantiated without its required dependencies.
