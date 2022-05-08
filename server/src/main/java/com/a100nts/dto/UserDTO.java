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
public class UserDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private boolean ranking;
	private List<Long> visitedSites;
	private List<String> sitesTime;

}
