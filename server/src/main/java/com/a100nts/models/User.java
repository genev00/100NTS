package com.a100nts.models;

import com.a100nts.utils.ListToStringConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstName;

	private String lastName;

	@Email
	private String email;

	private String password;

	private boolean ranking;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Vote> votes;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Comment> comments;

	@JoinTable (
			name = "visited_sites_by_user",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "site_id")
	)
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Site> sites;

	@Convert(converter = ListToStringConverter.class)
	private List<String> sitesTime;

}
