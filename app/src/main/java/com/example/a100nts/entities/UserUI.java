package com.example.a100nts.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUI {

    private String firstName;
    private String lastName;
    private String email;
    private boolean ranking;

}
