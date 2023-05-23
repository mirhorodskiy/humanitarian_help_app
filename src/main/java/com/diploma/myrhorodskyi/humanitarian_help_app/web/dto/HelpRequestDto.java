package com.diploma.myrhorodskyi.humanitarian_help_app.web.dto;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.enums.RequestCategory;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HelpRequestDto {

    private Long id;

    private String title;

    private RequestCategory category;

    private String location;

    private String description;

    private RequestStatus status;

    private Long userId;

    private Long volunteerId;


}
