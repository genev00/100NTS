package com.example.a100nts.entities;

public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean ranking;

    public User() {
    }

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