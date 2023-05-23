package com.diploma.myrhorodskyi.humanitarian_help_app.web.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class IllegalAccessException extends java.lang.IllegalAccessException {

    private HttpStatus httpStatus;

    public IllegalAccessException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public IllegalAccessException(String s, HttpStatus httpStatus) {
        super(s);
        this.httpStatus = httpStatus;
    }
}
