package com.a100nts.services.impl;

import com.a100nts.models.Site;
import com.a100nts.repositories.SiteRepository;
import com.a100nts.services.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteServiceImpl implements SiteService {

    @Autowired
    private SiteRepository siteRepository;

    @Override
    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }

    @Override
    public Site getDetails(Long id) {
        return siteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Site> getAllSitesBG() {
        return siteRepository.findAll();
    }

    @Override
    public Site getDetailsBG(Long id) {
        return siteRepository.findById(id).orElse(null);
    }

}
