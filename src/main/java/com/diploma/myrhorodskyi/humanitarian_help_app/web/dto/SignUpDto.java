package com.diploma.myrhorodskyi.humanitarian_help_app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpDto {

    String email;
    String username;
    String password;
    boolean isVolunteer;

}
