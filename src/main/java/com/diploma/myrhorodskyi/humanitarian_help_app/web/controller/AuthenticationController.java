package com.diploma.myrhorodskyi.humanitarian_help_app.web.controller;

import com.diploma.myrhorodskyi.humanitarian_help_app.web.dto.AuthenticationRequestDTO;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.dto.SignUpDto;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.service.impl.AuthenticationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@CrossOrigin(origins = "*",
        allowedHeaders = "*",
        exposedHeaders = "*",
        methods = {GET, POST, PUT, DELETE},
        maxAge = 3600)
@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;

    public AuthenticationController(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto signUpDto) {
        authenticationService.registration(signUpDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request) {

        String token = authenticationService.login(request);
        if (token == null)
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }



}
