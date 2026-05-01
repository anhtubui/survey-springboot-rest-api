# Exposing REST APIs with Spring Data REST

This phase focuses on automatically exposing RESTful endpoints for our JPA entities with minimal code using Spring Data REST.

## 11. Automated REST Endpoints

Spring Data REST builds on top of Spring Data repositories and automatically exports them as HTTP resources.

---

### Dependency Configuration

Ensure that `spring-boot-starter-data-rest` is included in your `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-rest</artifactId>
</dependency>
```

---

### Customizing the REST Resource

By default, Spring Data REST pluralizes the entity name (e.g., `/userDetailses`). We can customize the path and collection resource name using the `@RepositoryRestResource` annotation.

**UserDetailsRepository.java**:
```java
@RepositoryRestResource(path = "user-details")
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    @RestResource(rel = "by-role", path = "by-role")
    List<UserDetails> findByRole(String role);

    @RestResource(rel = "by-name", path = "by-name")
    List<UserDetails> findByName(String name);
}
```

---

### Key Features of Spring Data REST

1.  **HATEOAS Support**: The API responses include links to related resources and actions, following the "Hypermedia as the Engine of Application State" principle.
2.  **Pagination and Sorting**: 
    - Use query parameters like `?page=0&size=5` for pagination.
    - Use `?sort=name,desc` for sorting.
3.  **Automatic CRUD**:
    - `GET /user-details`: Retrieve all users (paginated).
    - `POST /user-details`: Create a new user.
    - `GET /user-details/{id}`: Retrieve a specific user.
    - `PUT /user-details/{id}`: Update a user.
    - `DELETE /user-details/{id}`: Delete a user.
4.  **Search Methods**: Custom query methods are automatically exposed under the `/search` endpoint.
    - **Default Behavior**: Without any annotation, the method name is used as the path.
        - *Method*: `List<UserDetails> findByRole(String role);`
        - *Endpoint*: `http://localhost:8080/user-details/search/findByRole?role=admin`
    - **With `@RestResource`**: We can customize the relation (`rel`) and the URL `path`.
        - *Method*: `@RestResource(path = "by-role") List<UserDetails> findByRole(String role);`
        - *Endpoint*: `http://localhost:8080/user-details/search/by-role?role=admin`

    Currently, we have customized our repository to use:
    - *By Role*: `http://localhost:8080/user-details/search/by-role?role=admin`
    - *By Name*: `http://localhost:8080/user-details/search/by-name?name=anhtu`

---

### Testing the API

Once the application is running, you can test the endpoints using a browser or Postman:

- **Base URL**: `http://localhost:8080/user-details`
- **Metadata**: Access `http://localhost:8080/profile/user-details` to see the ALPS (Application-Level Profile Semantics) metadata.

> [!TIP]
> If changes to the repository don't seem to reflect in the REST API, try restarting the application. While Spring Boot DevTools helps with many changes, some repository structure changes may require a full restart.
