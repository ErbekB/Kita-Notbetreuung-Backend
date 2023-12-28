package com.example.kitanotbetreuungbackend.kind;

import com.example.kitanotbetreuungbackend.kitaGruppe.KitaGruppe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KindRepository extends JpaRepository<Kind, Long> {
    List<Kind> findAllByKitaGruppe(KitaGruppe adminKitagruppe);
}
