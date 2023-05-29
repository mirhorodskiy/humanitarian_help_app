package com.diploma.myrhorodskyi.humanitarian_help_app.web.service;

import com.diploma.myrhorodskyi.humanitarian_help_app.web.dto.HelpRequestDto;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.error.IllegalAccessException;

import java.util.List;

public interface HelpRequestService {

    HelpRequestDto createRequest(HelpRequestDto helpRequestDto, String token);

    List<HelpRequestDto> getAllRequests();

    List<HelpRequestDto> getAllOpenRequests();

    List<HelpRequestDto> getProcessingRequests();

    void approveProcessedRequest(Long id);

    void denyProcessedRequest(Long id);

    List<HelpRequestDto> getUserRequests(String token);

    HelpRequestDto getHelpRequest(Long id);

    void deleteRequest(Long id, String token) throws IllegalAccessException;

    HelpRequestDto changeRequest(HelpRequestDto helpRequestDto, String token) throws IllegalAccessException;

    void approveRequestByUser(Long requestId, String token) throws IllegalAccessException;

    void takeRequestByVolunteer(Long requestId, String token) throws IllegalAccessException;

    List<HelpRequestDto> getVolunteersRequestsInProgress(String token);
}
