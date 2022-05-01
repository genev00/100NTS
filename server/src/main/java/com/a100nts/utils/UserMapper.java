package com.a100nts.utils;

import com.a100nts.dto.UserDTO;
import com.a100nts.models.User;

public final class UserMapper {

	private UserMapper() {
	}

	public static UserDTO userToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setEmail(user.getEmail());
		userDTO.setRanking(user.isRanking());
		return userDTO;
	}

}
