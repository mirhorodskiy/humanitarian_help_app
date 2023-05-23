package com.diploma.myrhorodskyi.humanitarian_help_app.web.controller;

import com.diploma.myrhorodskyi.humanitarian_help_app.web.dto.UserDto;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUser(HttpServletRequest request) {
        UserDto user = userService.getUser(request.getHeader(HttpHeaders.AUTHORIZATION));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/profile/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,
                                                        HttpServletRequest request) {
        UserDto user = userService.updateUser(userDto, request.getHeader(HttpHeaders.AUTHORIZATION));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


}
