package com.diploma.myrhorodskyi.humanitarian_help_app.web.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    private String email;
    private String password;
}
