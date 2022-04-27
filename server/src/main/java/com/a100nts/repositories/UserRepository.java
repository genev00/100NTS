package com.a100nts.repositories;

import com.a100nts.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(Long id);
    Optional<User> findByEmail(String email);

}
