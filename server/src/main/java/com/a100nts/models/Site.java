package com.a100nts.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @Column(length = 2048)
    private String description;
    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL)
    private List<SiteImage> images;
    private double rating;

    public Site(Long id, String name, String description, List<SiteImage> images, double rating) {
        this.id = id;
        this.title = name;
        this.description = description;
        this.images = images;
        this.rating = rating;
    }

    public Site() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SiteImage> getImages() {
        return images;
    }

    public void setImages(List<SiteImage> images) {
        this.images = images;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
