package com.a100nts.services.impl;

import com.a100nts.dto.SiteDTO;
import com.a100nts.utils.SiteMapper;
import com.a100nts.models.Site;
import com.a100nts.repositories.SiteRepository;
import com.a100nts.services.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SiteServiceImpl implements SiteService {

    @Autowired
    private SiteRepository siteRepository;

    @Override
    public List<SiteDTO> getAllSites() {
        return siteRepository.findAll().stream()
                .map(SiteMapper::siteToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SiteDTO getDetails(Long id) {
        Optional<Site> site = siteRepository.findById(id);
        return site.map(SiteMapper::siteToDTO).orElse(null);
    }

    @Override
    public List<SiteDTO> getAllSitesBG() {
        return siteRepository.findAll().stream()
                .map(SiteMapper::siteToDTOBG)
                .collect(Collectors.toList());
    }

    @Override
    public SiteDTO getDetailsBG(Long id) {
        Optional<Site> site = siteRepository.findById(id);
        return site.map(SiteMapper::siteToDTOBG).orElse(null);
    }

}
