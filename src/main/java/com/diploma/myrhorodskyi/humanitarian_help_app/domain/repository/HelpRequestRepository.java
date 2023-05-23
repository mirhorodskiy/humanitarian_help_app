package com.diploma.myrhorodskyi.humanitarian_help_app.domain.repository;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.HelpRequest;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.Volunteer;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.enums.RequestCategory;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.enums.RequestStatus;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.enums.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HelpRequestRepository extends CrudRepository<HelpRequest, Long> {


    List<HelpRequest> findAll();

    List<HelpRequest> findAllByStatus(RequestStatus status);

    List<HelpRequest> findAllByCategory(RequestCategory category);

    List<HelpRequest> findAllByVolunteerId(Long volunteerId);

    List<HelpRequest> findHelpRequestByUserId(Long userId);

    HelpRequest findHelpRequestById(Long id);
}
