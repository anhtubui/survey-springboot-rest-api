# REST API: HTTP Methods and Status Codes

Understanding the standard HTTP methods and status codes is essential for building and consuming RESTful APIs. This document outlines the most common practices used in this project.

## Request Methods in REST API

The choice of HTTP method clearly communicates the intended action on a resource.

- **GET**: Used to retrieve details of a resource, such as a survey or a specific question.
- **POST**: Used to create a new resource, like adding a new survey.
- **PUT**: Updates an **entire** existing resource (Full Replacement).
- **PATCH**: Updates **part** of a resource (Partial Update).
- **DELETE**: Removes a specific resource.

### PUT vs. PATCH: Key Differences

| Feature      | PUT (Full Replacement)             | PATCH (Partial Update)                  |
| :----------- | :--------------------------------- | :-------------------------------------- |
| **Payload**  | Must send the **entire** object.   | Send only **modified** fields.          |
| **Behavior** | Replaces the resource completely.  | Updates specific parts of the resource. |
| **Example**  | Saving a full "Edit Profile" form. | Toggling a status or renaming a title.  |

### Real-World Example Implementation

In a Spring Boot application, the implementation of these methods would look like this:

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    // 1. PUT: Replace the entire user profile
    @PutMapping("/{id}")
    public User replaceUser(@PathVariable Long id, @RequestBody User newUser) {
        // Logic: Find user by id and replace ALL fields with data from newUser.
        // If 'newUser' is missing fields, those fields will be set to null/default in the database.
        return userService.save(newUser);
    }

    // 2. PATCH: Update only specific fields (e.g., email address)
    @PatchMapping("/{id}")
    public User updateEmail(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        // Logic: Find user by id and apply ONLY the fields present in the 'updates' map.
        // Other fields like 'username' or 'password' remain unchanged.
        return userService.partialUpdate(id, updates);
    }
}
```

---

## Response Status Codes in REST API

Status codes inform the client about the result of their request.

- **200 OK**: Indicates a successful request.
- **201 Created**: The request was successful, and a new resource was created (typically after a POST).
- **204 No Content**: The request was successful, but there is no response body (common for successful DELETE or PUT/PATCH where the update is confirmed but not returned).
- **400 Bad Request**: The server cannot process the request due to client error (e.g., validation errors).
- **401 Unauthorized**: Missing or incorrect credentials for access.
- **404 Not Found**: The requested resource does not exist.
- **500 Internal Server Error**: An unexpected error occurred on the server while processing the request.

---

## Overall Best Practices

1.  **Use Appropriate Methods**: Always use the correct HTTP method to ensure the API is predictable and RESTful.
2.  **Return Correct Status Codes**: Inform the consumer accurately. For example, don't return `200 OK` if the resource wasn't found; return `404 Not Found`.
3.  **Consistency**: Follow these standards throughout the entire API to improve reliability and usability for developers.
