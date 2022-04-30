package com.a100nts.utils;

import com.a100nts.dto.SiteDTO;
import com.a100nts.models.Site;
import com.a100nts.models.SiteImage;

import java.util.stream.Collectors;

public class SiteMapper {

    public static SiteDTO siteToDTO(Site site) {
        SiteDTO siteDTO = new SiteDTO();
        siteDTO.setTitle(site.getTitle());
        siteDTO.setProvince(site.getProvince());
        siteDTO.setTown(site.getTown());
        siteDTO.setDetails(site.getDescription());
        siteDTO.setImageUrls(site.getImages().stream().map(SiteImage::getSrc).collect(Collectors.toList()));
        siteDTO.setRating(site.getRating());
        return siteDTO;
    }

    public static SiteDTO siteToDTOBG(Site site) {
        SiteDTO siteDTO = new SiteDTO();
        siteDTO.setTitle(site.getTitleBG());
        siteDTO.setProvince(site.getProvinceBG());
        siteDTO.setTown(site.getTownBG());
        siteDTO.setDetails(site.getDescriptionBG());
        siteDTO.setImageUrls(site.getImages().stream().map(SiteImage::getSrc).collect(Collectors.toList()));
        siteDTO.setRating(site.getRating());
        return siteDTO;
    }

}
