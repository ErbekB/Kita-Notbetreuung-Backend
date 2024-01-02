package com.example.kitanotbetreuungbackend.user;

import com.example.kitanotbetreuungbackend.kita.Kita;
import com.example.kitanotbetreuungbackend.kitaGruppe.KitaGruppe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNameAndPasswort(String name, String passwort);
    Optional<User> findByName(String name);

    List<User> findAllByKita(Kita kita);
}
