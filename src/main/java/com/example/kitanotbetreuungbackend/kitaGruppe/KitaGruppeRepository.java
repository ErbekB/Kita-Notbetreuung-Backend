package com.example.kitanotbetreuungbackend.kitaGruppe;

import com.example.kitanotbetreuungbackend.kita.Kita;
import com.example.kitanotbetreuungbackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KitaGruppeRepository extends JpaRepository<KitaGruppe, Long> {
    Optional<KitaGruppe> findByNameAndKita(String kitaGruppeName, Kita kita);

    KitaGruppe findByAdmin(User sessionUser);

}
