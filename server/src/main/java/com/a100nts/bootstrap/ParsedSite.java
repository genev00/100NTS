package com.a100nts.bootstrap;

import java.util.List;

public class ParsedSite {

    private String title;
    private String description;
    private List<String> images;

    public ParsedSite() {
    }

    public ParsedSite(String name, String description, List<String> images, double rating) {
        this.title = name;
        this.description = description;
        this.images = images;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

}
