package com.diploma.myrhorodskyi.humanitarian_help_app.web.mapper;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.User;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.enums.Role;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.enums.Status;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserMapperService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapperService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }

    public User toEntity(UserDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .role(Role.USER)
                .status(Status.ACTIVE).build();
    }
}
