package com.diploma.myrhorodskyi.humanitarian_help_app.domain.repository;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.Volunteer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VolunteerRepository extends CrudRepository<Volunteer, Long> {
    boolean existsByEmail(String email);
    boolean existsByName(String name);
    Optional<Volunteer> findByEmail(String email);
    Optional<Volunteer> findByName(String name);
    List<Volunteer> findAll();

    @Query("select v from Volunteer v where v.isChecked = true")
    List<Volunteer> findAllByCheckedIsTrue();
}
