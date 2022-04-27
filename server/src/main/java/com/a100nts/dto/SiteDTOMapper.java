package com.a100nts.dto;

import com.a100nts.models.Site;
import com.a100nts.models.SiteImage;

import java.util.stream.Collectors;

public class SiteDTOMapper {

    public static SiteDTO toSiteDTO(Site site) {
        SiteDTO siteDTO = new SiteDTO();

        siteDTO.setTitle(site.getTitle());
        siteDTO.setDetails(site.getDescription());
        siteDTO.setImageUrls(site.getImages().stream().map(SiteImage::getSrc).collect(Collectors.toList()));
        siteDTO.setRating(site.getRating());

        return siteDTO;
    }
}
