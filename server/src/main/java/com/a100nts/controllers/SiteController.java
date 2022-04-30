package com.a100nts.controllers;

import com.a100nts.dto.SiteDTO;
import com.a100nts.services.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<SiteDTO>> getAllSites() {
        return new ResponseEntity<>(siteService.getAllSites(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SiteDTO> getDetails(@PathVariable Long id) {
        return new ResponseEntity<>(siteService.getDetails(id), HttpStatus.OK);
    }

    @GetMapping("/bg")
    public ResponseEntity<List<SiteDTO>> getAllSitesBG() {
        return new ResponseEntity<>(siteService.getAllSitesBG(), HttpStatus.OK);
    }

    @GetMapping("/bg/{id}")
    public ResponseEntity<SiteDTO> getDetailsBG(@PathVariable Long id) {
        return new ResponseEntity<>(siteService.getDetailsBG(id), HttpStatus.OK);
    }

}
