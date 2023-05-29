package com.diploma.myrhorodskyi.humanitarian_help_app.web.dto;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VolunteerDto {

    private Long id;

    private String name;

    private String description;

    private String email;

    private String phone;

    private String address;

    private String website;

    private Role role;

    private boolean isChecked;

}
