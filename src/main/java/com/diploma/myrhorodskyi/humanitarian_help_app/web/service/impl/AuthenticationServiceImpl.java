package com.diploma.myrhorodskyi.humanitarian_help_app.web.service.impl;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.User;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.UserCredentials;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.Volunteer;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.enums.Role;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.enums.Status;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.repository.UserCredentialsRepository;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.repository.UserRepository;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.repository.VolunteerRepository;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.dto.AuthenticationRequestDTO;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.dto.SignUpDto;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.error.SighUpException;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.mapper.UserMapperService;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.mapper.VolunteerMapperService;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl {

    private final UserRepository userRepository;
    private final VolunteerRepository volunteerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserCredentialsRepository userCredentialsRepository;
    private final UserMapperService userMapperService;
    private final VolunteerMapperService volunteerMapperService;


    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, VolunteerRepository volunteerRepository,
                                     UserMapperService userMapperService, PasswordEncoder passwordEncoder,
                                     AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                                     VolunteerMapperService volunteerMapperService, UserCredentialsRepository userCredentialsRepository) {
        this.userRepository = userRepository;
        this.volunteerRepository = volunteerRepository;
        this.userMapperService = userMapperService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.volunteerMapperService = volunteerMapperService;
        this.userCredentialsRepository = userCredentialsRepository;
    }

    public void createUser(SignUpDto signUpDto) {

        UserCredentials userCredentials = UserCredentials.builder()
                .email(signUpDto.getEmail())
                .role(Role.USER)
                .username(signUpDto.getUsername())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .build();
        userCredentialsRepository.save(userCredentials);

        userRepository.save(User.builder()
                .username(signUpDto.getUsername())
                .email(signUpDto.getEmail())
                .role(Role.USER)
                .status(Status.ACTIVE)
                .credentials(userCredentials)
                .build());

    }

    public void createVolunteer(SignUpDto signUpDto) {

        UserCredentials userCredentials = UserCredentials.builder()
                .email(signUpDto.getEmail())
                .role(Role.VOLUNTEER)
                .username(signUpDto.getUsername())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .build();
        userCredentialsRepository.save(userCredentials);

        volunteerRepository.save(Volunteer.builder()
                .email(signUpDto.getEmail())
                .isChecked(false)
                .name(signUpDto.getUsername())
                .role(Role.VOLUNTEER)
                .status(Status.ACTIVE)
                .credentials(userCredentials)
                .build());
    }

    public void registration(SignUpDto signUpDto) {

        if (userCredentialsRepository.existsByEmail(signUpDto.getEmail()))
            throw new SighUpException("Email is already exists", HttpStatus.FORBIDDEN);
        else if (userCredentialsRepository.existsByUsername(signUpDto.getUsername()))
            throw new SighUpException("Username is already exists", HttpStatus.FORBIDDEN);

        if (signUpDto.isVolunteer())
            createVolunteer(signUpDto);
        else
            createUser(signUpDto);
    }

    public String login(AuthenticationRequestDTO credits) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credits.getEmail(), credits.getPassword()));

        if (userRepository.existsByEmail(credits.getEmail())) {
            User user = userRepository.findByEmail(credits.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
            return jwtTokenProvider.createToken(credits.getEmail(), user.getRole().name());
        }

        if (volunteerRepository.existsByEmail(credits.getEmail())) {

            Volunteer volunteer = volunteerRepository.findByEmail(credits.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Volunteer doesn't exists"));
            return jwtTokenProvider.createToken(credits.getEmail(), volunteer.getRole().name());

        }
        return null;
    }

    public Role getRole(String token) {
        return userCredentialsRepository.findByEmail(getCredentialsEmailByToken(token)).get().getRole();
    }

    private String getCredentialsEmailByToken(String token) {
        return jwtTokenProvider.getUsername(token);
    }

}
