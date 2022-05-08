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
public class UserUI {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean ranking;
    private List<Long> visitedSites;
    private List<String> sitesTime;

}
