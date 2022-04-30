package com.a100nts.models;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL)
    private List<Comment> comments;

}
