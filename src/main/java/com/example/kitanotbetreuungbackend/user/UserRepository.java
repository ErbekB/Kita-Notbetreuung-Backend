package com.example.kitanotbetreuungbackend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNameAndPasswort(String name, String passwort);
    Optional<User> findByName(String name);
}
