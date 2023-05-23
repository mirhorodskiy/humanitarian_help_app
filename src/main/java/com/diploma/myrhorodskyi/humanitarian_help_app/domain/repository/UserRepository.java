package com.diploma.myrhorodskyi.humanitarian_help_app.domain.repository;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

}
