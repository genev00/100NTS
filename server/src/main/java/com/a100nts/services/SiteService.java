package com.a100nts.services;

import com.a100nts.models.Site;

import java.util.List;

public interface SiteService {

    List<Site> getAllSites();
    Site getDetails(Long id);
    List<Site> getAllSitesBG();
    Site getDetailsBG(Long id);

}
