package com.a100nts.models;

import javax.persistence.*;

@Entity
public class SiteImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Site site;
    private String src;

    public SiteImage() {
    }

    public SiteImage(String src, Site site) {
        this.site = site;
        this.src = src;
    }

    public Long getId() {
        return id;
    }

    public Site getSite() {
        return site;
    }

    public String getSrc() {
        return src;
    }
}