package com.a100nts.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

	private Long userId;
	private Long siteId;
	private Integer vote;
	private String language;

}