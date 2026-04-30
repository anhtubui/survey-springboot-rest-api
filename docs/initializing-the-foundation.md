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
