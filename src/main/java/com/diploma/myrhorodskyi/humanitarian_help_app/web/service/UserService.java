package com.diploma.myrhorodskyi.humanitarian_help_app.web.service;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.User;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.dto.UserDto;

public interface UserService {

    UserDto getUser(String token);

    UserDto updateUser(UserDto userDto, String token);

    User getUserEntity(String token);
}
