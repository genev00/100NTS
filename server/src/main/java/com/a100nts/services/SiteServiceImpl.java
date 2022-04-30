package com.a100nts.services;

import com.a100nts.dto.SiteDTO;
import com.a100nts.dto.SiteDTOMapper;
import com.a100nts.exceptions.SiteNotFoundException;
import com.a100nts.models.Site;
import com.a100nts.repositories.SiteRepository;
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
    public List<SiteDTO> getAllSites(boolean sorted) {
        if(sorted) {

        }
        return siteRepository.findAll().stream().map(SiteDTOMapper::toSiteDTO).collect(Collectors.toList());
    }

    @Override
    public SiteDTO getDetails(Long id) {
        Optional<Site> site = siteRepository.findById(id);
        if(site.isPresent()) {
            return SiteDTOMapper.toSiteDTO(site.get());
        }

        throw new SiteNotFoundException();
    }
}
