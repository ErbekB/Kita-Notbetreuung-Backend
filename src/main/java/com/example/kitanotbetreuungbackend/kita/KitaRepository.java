package com.example.kitanotbetreuungbackend.kita;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KitaRepository extends JpaRepository<Kita, Long> {
}
