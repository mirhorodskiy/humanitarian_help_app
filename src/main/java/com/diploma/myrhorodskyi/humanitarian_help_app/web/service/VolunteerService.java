package com.diploma.myrhorodskyi.humanitarian_help_app.web.service;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.Volunteer;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.dto.VolunteerDto;

import java.util.List;

public interface VolunteerService {
    VolunteerDto getVolunteerDto(String token);
    VolunteerDto updateVolunteer(VolunteerDto volunteerDto, String token);
    List<VolunteerDto> getAllVolunteers();
    Volunteer getVolunteerEntity(String token);
    VolunteerDto getVolunteerById(Long id);
    List<VolunteerDto> getUnapprovedVolunteers();

    void approveVolunteer(Long id);
}
