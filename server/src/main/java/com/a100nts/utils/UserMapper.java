package com.a100nts.utils;

import com.a100nts.dto.UserDTO;
import com.a100nts.models.User;

import java.util.List;
import java.util.stream.Collectors;

public final class UserMapper {

	private UserMapper() {
	}

	public static UserDTO userToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setEmail(user.getEmail());
		userDTO.setRanking(user.isRanking());
		userDTO.setVisitedSites(user.getSites().size());
		return userDTO;
	}

	public static List<UserDTO> usersToDTOs(List<User> users) {
		return users.stream()
				.map(UserMapper::userToDTO)
				.collect(Collectors.toList());
	}

}
