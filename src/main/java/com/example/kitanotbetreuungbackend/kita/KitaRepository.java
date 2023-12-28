package com.example.kitanotbetreuungbackend.kita;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KitaRepository extends JpaRepository<Kita, Long> {
    Optional<Kita> findByName(String kitaName);

    Optional<Kita> findByNameAndPostleitzahl(String kitaName, int postleitzahl);
}
