package com.diploma.myrhorodskyi.humanitarian_help_app.web.service.impl;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.Volunteer;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.repository.HelpRequestRepository;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.repository.VolunteerRepository;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.dto.VolunteerDto;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.mapper.VolunteerMapperService;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.security.JwtTokenProvider;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerServiceImpl implements VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final HelpRequestRepository helpRequestRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final VolunteerMapperService volunteerMapperService;

    @Autowired
    public VolunteerServiceImpl(VolunteerRepository volunteerRepository, HelpRequestRepository helpRequestRepository,
                                JwtTokenProvider jwtTokenProvider, VolunteerMapperService volunteerMapperService) {
        this.volunteerRepository = volunteerRepository;
        this.helpRequestRepository = helpRequestRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.volunteerMapperService = volunteerMapperService;
    }

    @Override
    public Volunteer getVolunteerEntity(String token) {
        return volunteerRepository.findByEmail(getEmailByToken(token))
                .orElseThrow(() -> new UsernameNotFoundException("Volunteer doesn't exists"));
    }

    @Override
    public VolunteerDto getVolunteerDto(String token) {
        Volunteer volunteer = volunteerRepository.findByEmail(getEmailByToken(token))
                .orElseThrow(() -> new UsernameNotFoundException("Volunteer doesn't exists"));
        return  volunteerMapperService.toDto(volunteer);
    }
    @Override
    public VolunteerDto getVolunteerById(Long id) {
        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Volunteer doesn't exists"));
        return  volunteerMapperService.toDto(volunteer);
    }


    public String getEmailByToken(String token) {
//        if(token == null || !token.startsWith("Bearer "))
//            throw new IllegalArgumentException("Header is null or doesn't start with 'Bearer '");
//        return jwtTokenProvider.getUsername(token.substring("Bearer".length()));
        return jwtTokenProvider.getUsername(token);
    }

    @Override
    public VolunteerDto updateVolunteer(VolunteerDto volunteerDto, String token) {
        Volunteer volunteer = volunteerRepository.findByEmail(getEmailByToken(token))
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));

        volunteer.setName(volunteerDto.getName() );
        volunteer.setAddress(volunteerDto.getAddress());
        volunteer.setDescription(volunteerDto.getDescription());
        volunteer.setPhone(volunteerDto.getPhone());
        volunteer.setWebsite(volunteerDto.getWebsite());

        return volunteerMapperService.toDto(volunteerRepository.save(volunteer));
    }

    @Override
    public List<VolunteerDto> getAllVolunteers() {
        List<Volunteer> volunteers = volunteerRepository.findAll();
        return volunteers.stream().map(volunteerMapperService::toDto).toList();
    }

    @Override
    public List<VolunteerDto> getUnapprovedVolunteers() {
        List<Volunteer> volunteers = volunteerRepository.findAllByCheckedIsTrue();
        return volunteers.stream().map(volunteerMapperService::toDto).toList();
    }

    @Override
    public void approveVolunteer(Long id) {
        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Volunteer doesn't exists"));
        volunteer.setChecked(true);
        volunteerRepository.save(volunteer);
    }
}