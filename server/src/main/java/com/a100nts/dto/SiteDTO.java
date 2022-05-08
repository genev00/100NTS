package com.a100nts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SiteDTO {

    private Long id;
    private String title;
    private String province;
    private String town;
    private String details;
    private List<String> imageUrls;
    private List<CommentDTO> comments;
    private double rating;
    private double latitude;
    private double longitude;

}
