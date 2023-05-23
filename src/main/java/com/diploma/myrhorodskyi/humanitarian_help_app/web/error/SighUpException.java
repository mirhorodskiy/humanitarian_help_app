package com.diploma.myrhorodskyi.humanitarian_help_app.web.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
public class SighUpException extends AuthenticationException {
    private HttpStatus httpStatus;

    public SighUpException(String msg) {
        super(msg);
    }

    public SighUpException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }
}
