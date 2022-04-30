package com.a100nts.models;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Size(min = 4, message = "First name should have at least 4 characters")
    private String firstName;
    @NotEmpty
    @Size(min = 4, message = "Last name should have at least 4 characters")
    private String lastName;
    @NotEmpty
    @Email
    @Column(unique = true)
    private String email;
    @NotEmpty
    @Size(min = 5, message = "Password should have at least 5 characters")
    private String password;
    private boolean ranking;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, boolean ranking) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.ranking = ranking;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRanking() {
        return ranking;
    }

    public void setRanking(boolean ranking) {
        this.ranking = ranking;
    }

}
