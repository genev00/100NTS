package com.a100nts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Site with this id doesn't exist")
public class SiteNotFoundException extends RuntimeException{

}
