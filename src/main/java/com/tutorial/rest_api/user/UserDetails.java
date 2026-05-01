package com.tutorial.rest_api.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity // Specifies that the class is an JPA entity and is mapped to a database table
public class UserDetails {

    // Default constructor
    public UserDetails() {
    }

    // Parameterized constructor
    public UserDetails(String name, String role) {
        super(); // Calls Object constructor (parent class)
        this.name = name;
        this.role = role;
    }

    @Id // Marks this field as the primary key of the entity
    @GeneratedValue // Indicates that the primary key value will be automatically generated
    private Long id;

    private String name;

    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDetails [id=" + id + ", name=" + name + ", role=" + role + "]";
    }

}
