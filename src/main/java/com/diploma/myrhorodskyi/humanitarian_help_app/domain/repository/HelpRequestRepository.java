package com.diploma.myrhorodskyi.humanitarian_help_app.domain.repository;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.HelpRequest;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.enums.RequestCategory;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.enums.RequestStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HelpRequestRepository extends CrudRepository<HelpRequest, Long> {

    List<HelpRequest> findAll();

    List<HelpRequest> findAllByStatus(RequestStatus status);

    List<HelpRequest> findAllByCategory(RequestCategory category);

    @Query("select h from HelpRequest h where h.volunteer.id = ?1 and h.status = 'IN_PROGRESS'")
    List<HelpRequest> findAllByVolunteerIdAndStatusInProgress(Long volunteer_id);

    List<HelpRequest> findHelpRequestByUserId(Long userId);

    HelpRequest findHelpRequestById(Long id);

    List<HelpRequest> findAllByLocation(String location);

    List<HelpRequest> findAllByLocationAndCategory(String location, RequestCategory category);

}
