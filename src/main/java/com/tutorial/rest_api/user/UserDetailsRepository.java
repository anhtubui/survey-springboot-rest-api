package com.tutorial.rest_api.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "user-details")
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    @RestResource(rel = "by-role", path = "by-role")
    List<UserDetails> findByRole(String role);

    @RestResource(rel = "by-name", path = "by-name")
    List<UserDetails> findByName(String name);
}
