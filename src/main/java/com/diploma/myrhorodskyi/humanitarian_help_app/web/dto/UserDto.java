package com.diploma.myrhorodskyi.humanitarian_help_app.web.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {

    private Long id;

    private String username;

    private String email;

    private String phone;

    public UserDto(Long id, String username, String email,String phone) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

}
