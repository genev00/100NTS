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
public class Site {

    private String title;
    private String province;
    private String town;
    private String details;
    private List<String> imageUrls;
    private List<Comment> comments;
    private double rating;

}
