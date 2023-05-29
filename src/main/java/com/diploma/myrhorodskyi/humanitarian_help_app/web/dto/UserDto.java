package com.diploma.myrhorodskyi.humanitarian_help_app.web.dto;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.enums.Role;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {

    private Long id;

    private String username;

    private String email;

    private String phone;

    private Role role;

    public UserDto(Long id, String username, String email,String phone, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

}
