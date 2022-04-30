package com.a100nts.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String titleBG;
    private String province;
    private String provinceBG;
    private String town;
    private String townBG;
    @Column(length = 2048)
    private String description;
    @Column(length = 2048)
    private String descriptionBG;
    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL)
    private List<SiteImage> images;
    private double rating;

}
