package com.a100nts.services;

import com.a100nts.bootstrap.ParseSiteMapper;
import com.a100nts.bootstrap.ParsedSite;
import com.a100nts.models.Site;
import com.a100nts.repositories.SiteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

@Service
public class DbSeedService {

    @Autowired
    private SiteRepository siteRepository;

    public static final String SITES_DATA_FILE = "src/main/resources/static/sites_data.json";
    private ParsedSite[] parsedSites;

    private void loadSitesFromJson() {
        File file = new File(SITES_DATA_FILE);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            parsedSites = objectMapper.readValue(file, ParsedSite[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fillDb() {
        loadSitesFromJson();

        HashSet<Site> sites = new HashSet<>();

        for(ParsedSite parseSite : parsedSites) {
            Site site = ParseSiteMapper.toSite(parseSite);
            sites.add(site);
        }

        siteRepository.saveAll(sites);
    }
}
