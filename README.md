# Questionnaire REST API Documentation

Welcome to the Questionnaire REST API project. This documentation is split into several logical sections to help you understand the architecture and development progress.

## Table of Contents

1.  **[Initializing the Foundation](docs/initializing-the-foundation.md)**
    - Data Models (Java Beans)
    - Survey Service Implementation
    - Key Components Summary

2.  **[Implementing the Survey Controller](docs/implementing-survey-controller.md)**
    - The Survey Resource Controller
    - Connecting the Layers
    - Testing the Endpoint
    - Key Concepts Applied

3.  **[Retrieving a Specific Survey](docs/retrieving-survey-by-id.md)**
    - Building the REST API for Specific Survey Retrieval
    - Captured URL Path Variables
    - Implementing Survey Lookup Logic
    - Handling Optional and Response Status
    - Testing the API Endpoint

4.  **[HTTP Methods and Status Codes](docs/http-methods-and-status-codes.md)**
    - Request Methods in REST API
    - Response Status Codes in REST API
    - Best Practices

5.  **[Building Survey Questions APIs](docs/survey-questions-api.md)**
    - Retrieving All Questions for a Survey
    - Retrieving a Specific Question by ID
    - Creating a New Survey Question (POST)
    - Hierarchical REST API Design

6.  **[ID Generation and REST Best Practices](docs/id-generation-and-response-status.md)**
    - Secure Random ID Generation
    - ResponseEntity and 201 Created Status
    - Dynamic Location Header with ServletUriComponentsBuilder

7.  **[Implementing the DELETE Method](docs/deleting-survey-questions.md)**
    - Hierarchical Delete Pattern
    - 204 No Content Status
    - Service Logic for Resource Removal

8.  **[Implementing the PUT Method](docs/updating-survey-questions.md)**
    - Resource Replacement Pattern
    - Request Body Binding with @RequestBody
    - Service Logic for Question Update

9.  **[Spring Data JPA and Data REST](docs/spring-data-jpa-and-data-rest.md)**
    - JPA Setup and H2 Configuration
    - Entity Modeling with UserDetails
    - Automated API Creation with Data REST

10. **[Using CommandLineRunner for Startup Operations](docs/startup-operations-with-command-line-runner.md)**
    - Startup Logic with CommandLineRunner
    - Constructor Injection for Repositories
    - Performing CRUD Operations at Startup
    - SQL Query Logging and Verification

---

## How to Run

To run the application, use the following command in your terminal:

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.
