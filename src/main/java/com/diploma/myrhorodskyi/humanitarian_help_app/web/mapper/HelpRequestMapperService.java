package com.diploma.myrhorodskyi.humanitarian_help_app.web.mapper;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.HelpRequest;
import com.diploma.myrhorodskyi.humanitarian_help_app.web.dto.HelpRequestDto;
import org.springframework.stereotype.Service;

@Service
public class HelpRequestMapperService {

    public HelpRequestDto toDto(HelpRequest helpRequest) {

        HelpRequestDto helpRequestDto = HelpRequestDto.builder()
                .id(helpRequest.getId())
                .title(helpRequest.getTitle())
                .category(helpRequest.getCategory())
                .status(helpRequest.getStatus())
                .description(helpRequest.getDescription())
                .location(helpRequest.getLocation())
                .userId(helpRequest.getUser().getId())
                .build();

        if (helpRequest.getVolunteer() != null)
            helpRequestDto.setVolunteerId(helpRequest.getVolunteer().getId());

        return helpRequestDto;
    }
}
