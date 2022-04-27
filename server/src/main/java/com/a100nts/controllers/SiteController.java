package com.a100nts.controllers;

import com.a100nts.dto.SiteDTO;
import com.a100nts.services.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sites")
public class SiteController {

    @Autowired
    private SiteService siteService;

    @GetMapping("/")
    public List<SiteDTO> getAllSites() {
        return siteService.getAllSites(false);
    }

    @GetMapping("/{id}")
    public SiteDTO getDetails(@PathVariable("id") Long id) {
        return siteService.getDetails(id);
    }
}
