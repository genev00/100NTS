package com.a100nts.services;


import com.a100nts.dto.SiteDTO;

import java.util.List;

public interface SiteService {

    List<SiteDTO> getAllSites();
    SiteDTO getDetails(Long id);
    List<SiteDTO> getAllSitesBG();
    SiteDTO getDetailsBG(Long id);

}
