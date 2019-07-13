package com.jeeProject.weka.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserUnauthorizedExecption extends RuntimeException {

    public UserUnauthorizedExecption(String exception) {
        super(exception);
    }
}
