package com.diploma.myrhorodskyi.humanitarian_help_app.web.controller;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.enums.Role;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.dto.AuthenticationRequestDTO;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.dto.SignUpDto;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.service.impl.AuthenticationServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request) throws LoginException {

        String token = authenticationService.login(request);
        if (token == null)
            throw new LoginException("Invalid email/password combination");
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("status", HttpStatus.OK.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @GetMapping("/role")
    public ResponseEntity<?> getRole(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        Role role = authenticationService.getRole(token);
        Map<String, Object> response = new HashMap<>();
        response.put("Role", role);
        response.put("status", HttpStatus.OK.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
