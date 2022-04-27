package com.a100nts.bootstrap;

import com.a100nts.models.Site;
import com.a100nts.models.SiteImage;

import java.util.stream.Collectors;

public class ParseSiteMapper {

    public static Site toSite(ParsedSite parsedSite) {
        Site site = new Site();
        site.setTitle(parsedSite.getTitle());
        site.setDescription(parsedSite.getDescription());
        site.setImages(parsedSite.getImages()
                .stream()
                .map(src -> new SiteImage(src, site)).collect(Collectors.toList()));

        return site;
    }
}
