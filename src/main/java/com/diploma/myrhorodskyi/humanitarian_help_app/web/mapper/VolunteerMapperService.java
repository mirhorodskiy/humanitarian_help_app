package com.diploma.myrhorodskyi.humanitarian_help_app.web.mapper;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.Volunteer;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.enums.Role;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.enums.Status;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.dto.VolunteerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class VolunteerMapperService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public VolunteerMapperService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public VolunteerDto toDto(Volunteer volunteer) {
        return VolunteerDto.builder()
                .id(volunteer.getId())
                .name(volunteer.getName())
                .email(volunteer.getEmail())
                .phone(volunteer.getPhone())
                .address(volunteer.getAddress())
                .description(volunteer.getDescription())
                .website(volunteer.getWebsite())
                .isChecked(volunteer.isChecked())
                .build();
    }

    public Volunteer toEntity(VolunteerDto volunteerDto) {
        return Volunteer.builder()
                .name(volunteerDto.getName())
                .description(volunteerDto.getDescription())
                .email(volunteerDto.getEmail())
                .phone(volunteerDto.getPhone())
                .address(volunteerDto.getAddress())
                .website(volunteerDto.getWebsite())
                .isChecked(false)
                .role(Role.VOLUNTEER)
                .status(Status.ACTIVE).build();
    }

}
