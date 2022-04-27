package com.a100nts.dto;

import java.util.List;

public class SiteDTO {

    private String title;
    private String details;
    private List<String> imageUrls;
    private double rating;

    public SiteDTO(String title, String details, List<String> imageUrls, double rating) {
        this.title = title;
        this.details = details;
        this.imageUrls = imageUrls;
        this.rating = rating;
    }

    public SiteDTO() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
