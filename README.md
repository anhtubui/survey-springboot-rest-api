Below is a comprehensive guide to initializing your Questionnaire REST API. This document outlines the foundational data structures and the service layer required to manage surveys and questions within a Spring Boot application.

---

# Questionnaire REST API: Initializing the Foundation

This document details the initial setup of the data models and service layer for the Questionnaire API. The architecture follows the **Java Bean** convention and utilizes **Spring's Service Layer** to manage in-memory data before transitioning to a persistent database.

## 1. Data Models (Java Beans)

We begin by defining the core entities: `Survey` and `Question`. These classes serve as the blueprints for our data.

### The Question Model

The `Question` class encapsulates the individual queries within a survey.

```java
public class Question {
    private String id;
    private String description;
    private List<String> options;
    private String correctAnswer;

    // Constructors
    public Question() {}

    public Question(String id, String description, List<String> options, String correctAnswer) {
        this.id = id;
        this.description = description;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    // Getters and Setters
    // toString() method
}
```

### The Survey Model

The `Survey` class acts as a container for a collection of questions.

```java
public class Survey {
    private String id;
    private String title;
    private String description;
    private List<Question> questions;

    // Constructors
    public Survey() {}

    public Survey(String id, String title, String description, List<Question> questions) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.questions = questions;
    }

    // Getters and Setters
    // toString() method
}
```

---

## 2. Survey Service Implementation

The `SurveyService` class is the business logic layer. By annotating it with `@Service`, we allow Spring to manage it as a **Singleton Bean**, making it injectable into our future Controllers.

### Static Data Initialization

For the initial development phase, we use a `static` list to simulate a database.

```java
@Service
public class SurveyService {

    private static List<Survey> surveys = new ArrayList<>();

    static {
        // Initialize Sample Questions
        Question q1 = new Question("Q1", "Most popular Cloud Platform",
                        Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle"), "AWS");
        Question q2 = new Question("Q2", "Fastest growing Language",
                        Arrays.asList("Java", "Python", "JavaScript", "Swift"), "Python");

        List<Question> questions = new ArrayList<>(Arrays.asList(q1, q2));

        // Initialize Sample Survey
        Survey survey = new Survey("Survey1", "Tech Survey", "A survey about modern technology", questions);

        surveys.add(survey);
    }

    public List<Survey> retrieveAllSurveys() {
        return surveys;
    }
}
```

---

## 3. Key Components Summary

| Component        | Responsibility                                                                                |
| :--------------- | :-------------------------------------------------------------------------------------------- |
| **Java Beans**   | Encapsulation of data using private fields, getters, and setters.                             |
| **Collections**  | Using `List<Question>` to maintain a one-to-many relationship between Survey and Questions.   |
| **@Service**     | Identifies the class as a provider of business logic and enables Spring Dependency Injection. |
| **Static Block** | Ensures sample data is populated exactly once when the class is loaded.                       |

---

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

## Next Steps

With the collection retrieval working, the next phase will focus on **Resource Specificity**:

- Implementing **Path Variables** to retrieve a single survey by its unique ID (e.g., `/surveys/{surveyId}`).
- Filtering questions within a specific survey.
