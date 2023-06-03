package com.diploma.myrhorodskyi.humanitarian_help_app.web.service.impl;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.User;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.repository.UserRepository;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.dto.UserDto;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.mapper.UserMapperService;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.security.JwtTokenProvider;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapperService userMapperService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapperService userMapperService, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.userMapperService = userMapperService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public User getUserEntity(String token) {
        return userRepository.findByEmail(getEmailByToken(token))
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
        return userMapperService.toDto(user);
    }


    public UserDto getUser(String token) {
        User user = userRepository.findByEmail(getEmailByToken(token))
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
        return  userMapperService.toDto(user);
    }

    public String getEmailByToken(String token) {
//        if(token == null || !token.startsWith("Bearer "))
//            throw new IllegalArgumentException("Header is null or doesn't start with 'Bearer '");
//        return jwtTokenProvider.getUsername(token.substring("Bearer".length()));
        return jwtTokenProvider.getUsername(token);
    }

    public UserDto updateUser(UserDto userDto, String token) {
        User user = userRepository.findByEmail(getEmailByToken(token))
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));

        user.setPhone(userDto.getPhone());
        user.setUsername(userDto.getUsername());

        return userMapperService.toDto(userRepository.save(user));
    }

}
