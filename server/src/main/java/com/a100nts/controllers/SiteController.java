package com.a100nts.controllers;

import com.a100nts.dto.SiteDTO;
import com.a100nts.models.Site;
import com.a100nts.services.SiteService;
import com.a100nts.web.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.a100nts.utils.SiteMapper.siteToDTO;
import static com.a100nts.utils.SiteMapper.siteToDTOBG;
import static com.a100nts.utils.SiteMapper.sitesToDTOs;
import static com.a100nts.utils.SiteMapper.sitesToDTOsBG;

@RestController
@RequestMapping("/api/v1/sites")
public class SiteController {

    @Autowired
    private SiteService siteService;

    @GetMapping
    public ResponseEntity<List<SiteDTO>> getAllSites() {
        return new ResponseEntity<>(sitesToDTOs(siteService.getAllSites()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SiteDTO> getDetails(@PathVariable Long id) {
        return new ResponseEntity<>(siteToDTO(siteService.getDetails(id)), HttpStatus.OK);
    }

    @GetMapping("/bg")
    public ResponseEntity<List<SiteDTO>> getAllSitesBG() {
        return new ResponseEntity<>(sitesToDTOsBG(siteService.getAllSitesBG()), HttpStatus.OK);
    }

    @GetMapping("/bg/{id}")
    public ResponseEntity<SiteDTO> getDetailsBG(@PathVariable Long id) {
        return new ResponseEntity<>(siteToDTOBG(siteService.getDetailsBG(id)), HttpStatus.OK);
    }

    @PostMapping("/vote")
    public ResponseEntity<SiteDTO> voteSite(@RequestBody Vote vote) {
        Site site = siteService.voteSite(vote);
        SiteDTO siteDTO;
        if (vote.getLanguage().equals("bg")) {
            siteDTO = siteToDTOBG(site);
        } else {
            siteDTO = siteToDTO(site);
        }
        return new ResponseEntity<>(siteDTO, HttpStatus.OK);
    }

    @GetMapping("/vote/get/{userId}/{siteId}")
    public ResponseEntity<Integer> getVoteForSite(@PathVariable Long userId, @PathVariable Long siteId) {
        return new ResponseEntity<>(siteService.getUserVoteForSite(userId, siteId), HttpStatus.OK);
    }

}
