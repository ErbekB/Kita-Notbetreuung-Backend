package com.example.kitanotbetreuungbackend.kitaGruppe;

import com.example.kitanotbetreuungbackend.kita.Kita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KitaGruppeRepository extends JpaRepository<KitaGruppe, Long> {
    Optional<KitaGruppe> findByName(String kitaGruppeName);

    Optional<KitaGruppe> findByNameAndKita(String kitaGruppeName, Kita kita);
}
