package com.diploma.myrhorodskyi.humanitarian_help_app.web.service.impl;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.HelpRequest;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.User;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.enums.RequestStatus;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.repository.HelpRequestRepository;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.dto.HelpRequestDto;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.error.IllegalAccessException;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.mapper.HelpRequestMapperService;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.service.HelpRequestService;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.service.UserService;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class HelpRequestServiceImpl implements HelpRequestService {

    private final UserService userService;
    private final VolunteerService volunteerService;
    private final HelpRequestRepository helpRequestRepository;
    private final HelpRequestMapperService helpRequestMapperService;

    @Autowired
    public HelpRequestServiceImpl(UserService userService, VolunteerService volunteerService, HelpRequestRepository helpRequestRepository, HelpRequestMapperService helpRequestMapperService) {
        this.userService = userService;
        this.volunteerService = volunteerService;
        this.helpRequestRepository = helpRequestRepository;
        this.helpRequestMapperService = helpRequestMapperService;
    }


    @Override
    public HelpRequestDto createRequest(HelpRequestDto helpRequestDto, String token) {
        HelpRequest helpRequest = HelpRequest.builder()
                .category(helpRequestDto.getCategory())
                .description(helpRequestDto.getDescription())
                .status(RequestStatus.PROCESSING)
                .location(helpRequestDto.getLocation())
                .title(helpRequestDto.getTitle())
                .user(userService.getUserEntity(token))
                .build();
        helpRequestRepository.save(helpRequest);

        return helpRequestMapperService.toDto(helpRequest);
    }

    @Override
    public List<HelpRequestDto> getAllRequests() {
        List<HelpRequest> requests = helpRequestRepository.findAll();
        return requests.stream().map(helpRequestMapperService::toDto).toList();
    }

    @Override
    public List<HelpRequestDto> getAllOpenRequests() {
        List<HelpRequest> requests = helpRequestRepository.findAllByStatus(RequestStatus.SEEKING_VOLUNTEER);
        return requests.stream().map(helpRequestMapperService::toDto).toList();
    }

    @Override
    public List<HelpRequestDto> getProcessingRequests() {
        List<HelpRequest> requests = helpRequestRepository.findAllByStatus(RequestStatus.PROCESSING);
        return requests.stream().map(helpRequestMapperService::toDto).toList();
    }

    @Override
    public void approveProcessedRequest(Long id) {
        HelpRequest request = helpRequestRepository.findHelpRequestById(id);
        request.setStatus(RequestStatus.SEEKING_VOLUNTEER);
        helpRequestRepository.save(request);
    }

    @Override
    public void denyProcessedRequest(Long id) {
        HelpRequest request = helpRequestRepository.findHelpRequestById(id);
        request.setStatus(RequestStatus.DENIED);
        helpRequestRepository.save(request);
    }

    @Override
    public List<HelpRequestDto> getUserRequests(String token) {
        List<HelpRequest> requests = helpRequestRepository.findHelpRequestByUserId(userService.getUser(token).getId());
        return requests.stream().map(helpRequestMapperService::toDto).toList();
    }

    @Override
    public HelpRequestDto getHelpRequest(Long id) {
        HelpRequest request = helpRequestRepository.findHelpRequestById(id);
        return helpRequestMapperService.toDto(request);
    }

    @Override
    public void deleteRequest(Long id, String token) throws IllegalAccessException {
        if(!checkIfRequestWasCreatedByUser(token, id))
            throw new IllegalAccessException("User has no permission", HttpStatus.FORBIDDEN);
        helpRequestRepository.delete(helpRequestRepository.findHelpRequestById(id));
    }

    @Override
    public HelpRequestDto changeRequest(HelpRequestDto helpRequestDto, String token) throws IllegalAccessException {
        if(!checkIfRequestWasCreatedByUser(token, helpRequestDto.getId()))
            throw new IllegalAccessException("User has no permission", HttpStatus.FORBIDDEN);
        HelpRequest helpRequest = helpRequestRepository.findHelpRequestById(helpRequestDto.getId());
        if(helpRequest.getStatus().equals(RequestStatus.COMPLETED) ||
                helpRequest.getStatus().equals(RequestStatus.IN_PROGRESS))
            throw new IllegalAccessException("Help request is already completed or in progress", HttpStatus.FORBIDDEN);

        helpRequest.setDescription(helpRequestDto.getDescription());
        helpRequest.setLocation(helpRequestDto.getLocation());
        helpRequest.setTitle(helpRequestDto.getTitle());
        helpRequest.setCategory(helpRequestDto.getCategory());

        helpRequestRepository.save(helpRequest);
        return helpRequestMapperService.toDto(helpRequest);
    }

    @Override
    public void approveRequestByUser(Long requestId, String token) throws IllegalAccessException {

        if(!checkIfRequestWasCreatedByUser(token, requestId))
            throw new IllegalAccessException("User has no permission", HttpStatus.FORBIDDEN);
        HelpRequest helpRequest = helpRequestRepository.findHelpRequestById(requestId);

        if(!helpRequest.getStatus().equals(RequestStatus.IN_PROGRESS))
            throw new IllegalAccessException("You cant approve not in progress request", HttpStatus.FORBIDDEN);

        helpRequest.setStatus(RequestStatus.COMPLETED);
        helpRequestRepository.save(helpRequest);
    }

    @Override
    public void takeRequestByVolunteer(Long requestId, String token) throws IllegalAccessException {

        HelpRequest helpRequest = helpRequestRepository.findHelpRequestById(requestId);
        if(!helpRequest.getStatus().equals(RequestStatus.SEEKING_VOLUNTEER))
            throw new IllegalAccessException("Request is in processing or was taken by another volunteer",
                    HttpStatus.FORBIDDEN);
        helpRequest.setStatus(RequestStatus.IN_PROGRESS);
        helpRequest.setVolunteer(volunteerService.getVolunteerEntity(token));
        helpRequestRepository.save(helpRequest);
    }

    private boolean checkIfRequestWasCreatedByUser(String token, Long id) {
        return Objects.equals(helpRequestRepository.findHelpRequestById(id).getUser().getId(), userService.getUser(token).getId());
    }
}
