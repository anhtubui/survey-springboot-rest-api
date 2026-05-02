# Securing the REST API

This phase focuses on protecting our REST API using Spring Security to ensure that only authorized users can access the resources.

## 21. Introduction to Spring Security

By default, any API we create is open to the public. To secure it, we integrate Spring Security.

---

### Adding the Dependency

The first step is to add the `spring-boot-starter-security` dependency to our project.

**pom.xml**:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

---

### Default Security Behavior

Once the dependency is added, Spring Security automatically:
1.  **Protects all endpoints**: Any request to the API will now require authentication.
2.  **Enables Form Login and Basic Auth**: You can log in via a browser or send credentials in the `Authorization` header.
3.  **Generates a Password**: A default username `user` is created, and a random password is printed in the application logs during startup:
    ```text
    Using generated security password: [UUID]
    ```

---

### Testing Secured Endpoints

If you attempt to access an endpoint (e.g., `/surveys`) without authentication, the server will return:
-   **401 Unauthorized**: If no credentials are provided.
-   **A Login Page**: If accessed via a browser.

To access the API via tools like Postman or `curl`, you must use **Basic Authentication** with the generated credentials.

---

### Next Steps

The default behavior of generating a new password every time the application restarts is not ideal for development. In the next sections, we will look at customizing these credentials and configuring role-based access control.
