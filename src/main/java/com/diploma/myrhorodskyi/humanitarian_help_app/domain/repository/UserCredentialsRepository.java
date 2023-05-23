package com.diploma.myrhorodskyi.humanitarian_help_app.domain.repository;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.User;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.UserCredentials;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends CrudRepository<UserCredentials, Long> {
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
    Optional<UserCredentials> findByEmail(String email);
}
