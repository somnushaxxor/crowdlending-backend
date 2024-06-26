package com.example.crowdlending.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.crowdlending.repository.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);
}
