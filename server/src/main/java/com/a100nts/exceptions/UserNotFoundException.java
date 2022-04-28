package com.a100nts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User with this nick name does not exist")
public class UserNotFoundException extends RuntimeException{
}
