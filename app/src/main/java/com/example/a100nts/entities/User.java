package com.example.a100nts.entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean ranking;
    private List<Comment> comments;
    private List<String> sitesTime;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String firstName, String lastName, String email, String password, boolean ranking) {
        this(email, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.ranking = ranking;
    }

    public User(Long id, String firstName, String lastName, String email, String password, boolean ranking) {
        this(firstName, lastName, email, password, ranking);
        this.id = id;
    }

}