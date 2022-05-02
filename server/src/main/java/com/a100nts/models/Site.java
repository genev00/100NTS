package com.a100nts.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Site {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String titleBG;

	private String province;

	private String provinceBG;

	private String town;

	private String townBG;

	@Column(length = 2048)
	private String description;

	@Column(length = 2048)
	private String descriptionBG;

	private double rating;

	@OneToMany(mappedBy = "site", cascade = CascadeType.ALL)
	private List<SiteImage> images;

	@OneToMany(mappedBy = "site", cascade = CascadeType.ALL)
	private List<Comment> comments;

	@JoinTable (
			name = "visited_sites_by_user",
			joinColumns = @JoinColumn(name = "site_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	@ManyToMany
	private List<User> visitedByUsers;

}
