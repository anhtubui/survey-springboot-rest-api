# Customizing Spring Security Configuration

This phase focuses on replacing the default security behavior with a custom configuration tailored for a REST API, including in-memory users and Basic Authentication.

## 22. Custom Users and Basic Auth Configuration

For REST APIs, we typically disable form-based login and CSRF protection while enabling Basic Authentication to allow programmatic access.

---

### Key Configuration Steps

1.  **Password Encoding**: Use `BCryptPasswordEncoder` to safely store user passwords.
2.  **User Management**: Define users in an `InMemoryUserDetailsManager` with specific roles (e.g., `USER`, `ADMIN`).
3.  **Security Filter Chain**:
    - **Authorize all requests**: Ensure every endpoint is protected.
    - **Basic Auth**: Use `httpBasic(Customizer.withDefaults())` to enable standard HTTP header authentication.
    - **Disable CSRF**: REST APIs are typically stateless and use tokens or basic auth, so CSRF protection is often disabled to simplify POST/PUT requests.
    - **Disable Headers (Frames)**: Required if using the H2 console, as it uses frames which are blocked by default.

---

### Implementation

**SpringSecurityConfiguration.java**:
```java
@Configuration
public class SpringSecurityConfiguration {

    @Bean
    public InMemoryUserDetailsManager createUserDetailsManager() {
        UserDetails userDetails1 = createNewUser("admin", "admin");
        UserDetails userDetails2 = createNewUser("user", "user");

        return new InMemoryUserDetailsManager(userDetails1, userDetails2);
    }

    private UserDetails createNewUser(String username, String password) {
        Function<String, String> passwordEncoder = input -> passwordEncoder().encode(input);

        return User.builder()
                .passwordEncoder(passwordEncoder)
                .username(username)
                .password(password)
                .roles("USER", "ADMIN")
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
        http.httpBasic(withDefaults());
        http.csrf(csrf -> csrf.disable());
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}
```

---

### CSRF Protection: When to Enable vs. Disable

Cross-Site Request Forgery (CSRF) is an attack that tricks a logged-in user into submitting a malicious request to a web application where they are authenticated.

#### **When SHOULD you enable CSRF?**
You should enable CSRF protection if your application meets **both** of these criteria:
1.  **Browser-based**: The application is accessed primarily through a web browser.
2.  **Cookie-based Authentication**: The application uses session cookies (like `JSESSIONID`) to authenticate users.

*Example:* A traditional Monolith Spring Boot application using Thymeleaf and Spring Security with form login. The browser automatically attaches the session cookie to every request, making it vulnerable to forged requests from other tabs.

#### **When can you safely DISABLE CSRF?**
You can typically disable CSRF if:
1.  **Stateless API**: Your API does not use session cookies for authentication. Instead, it uses headers like `Authorization: Basic ...` or `Authorization: Bearer <JWT>`.
2.  **Non-browser Clients**: The API is strictly consumed by mobile apps, IoT devices, or other backend services that do not automatically manage cookies like a browser does.
3.  **Strict CORS Policy**: You have a robust Cross-Origin Resource Sharing (CORS) policy that prevents unauthorized domains from making requests.

**In this project:** We disable CSRF because we are building a **stateless REST API** that uses **Basic Authentication**. Browsers do not automatically attach Basic Auth credentials to cross-site requests in the same way they do with cookies, significantly reducing the risk of CSRF.

