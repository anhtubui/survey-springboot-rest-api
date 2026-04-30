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

---

## How to Run
To run the application, use the following command in your terminal:
```bash
./mvnw spring-boot:run
```
The API will be available at `http://localhost:8080`.
