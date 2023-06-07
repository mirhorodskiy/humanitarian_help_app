package com.diploma.myrhorodskyi.humanitarian_help_app.web.service;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.enums.RequestCategory;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.dto.HelpRequestDto;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.error.IllegalAccessException;

import java.util.List;

public interface HelpRequestService {

    HelpRequestDto createRequest(HelpRequestDto helpRequestDto, String token);

    List<HelpRequestDto> getAllRequests(String location, RequestCategory category, Boolean open);

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

    List<HelpRequestDto> getOpenRequestsByLocation(String location);

    List<HelpRequestDto> getOpenRequestsByCategory(RequestCategory category);

    List<HelpRequestDto> getOpenRequestsByCategoryAndLocation(String location, RequestCategory category);

    List<HelpRequestDto> getAllRequestsByLocation(String location);

    List<HelpRequestDto> getAllRequestsByCategory(RequestCategory category);

    List<HelpRequestDto> getAllRequestsByCategoryAndLocation(String location, RequestCategory category);

    List<HelpRequestDto> getAllRequestsByLocationAndOrCategory(String location, RequestCategory category);

    List<HelpRequestDto> getOpenRequestsByLocationAndOrCategory(String location, RequestCategory category);
}
