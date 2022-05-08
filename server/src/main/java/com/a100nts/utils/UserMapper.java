package com.a100nts.utils;

import com.a100nts.dto.UserDTO;
import com.a100nts.models.Site;
import com.a100nts.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class UserMapper {

	private UserMapper() {
	}

	public static UserDTO userToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setEmail(user.getEmail());
		userDTO.setRanking(user.isRanking());
		userDTO.setVisitedSites(getVisitedSites(user));
		userDTO.setSitesTime(user.getSitesTime());
		return userDTO;
	}

	private static List<Long> getVisitedSites(User user) {
		return user.getSites() != null
				? user.getSites().stream()
					.map(Site::getId)
					.collect(Collectors.toList())
				: new ArrayList<>();
	}

	public static List<UserDTO> usersToDTOs(List<User> users) {
		return users.stream()
				.map(UserMapper::userToDTO)
				.collect(Collectors.toList());
	}

}
