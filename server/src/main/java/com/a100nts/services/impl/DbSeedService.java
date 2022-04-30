package com.a100nts.services.impl;

import com.a100nts.models.Site;
import com.a100nts.repositories.SiteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Service
public class DbSeedService {

    public static final String SITES_DATA_FILE = "static/sites_data.json";
    private static Site[] parsedSites;

    static {
        loadSitesFromJson();
    }

    @Autowired
    private SiteRepository siteRepository;


    @SneakyThrows
    private static void loadSitesFromJson() {
        File file = new File(DbSeedService.class.getClassLoader().getResource(SITES_DATA_FILE).toURI());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            parsedSites = objectMapper.readValue(file, Site[].class);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void fillDb() {
        siteRepository.saveAll(Arrays.asList(parsedSites));
    }

}
