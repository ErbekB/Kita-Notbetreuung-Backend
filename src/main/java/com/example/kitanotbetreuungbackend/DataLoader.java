package com.example.kitanotbetreuungbackend;

import com.example.kitanotbetreuungbackend.kind.Kind;
import com.example.kitanotbetreuungbackend.kind.KindRepository;
import com.example.kitanotbetreuungbackend.kita.Kita;
import com.example.kitanotbetreuungbackend.kita.KitaRepository;
import com.example.kitanotbetreuungbackend.kitaGruppe.KitaGruppe;
import com.example.kitanotbetreuungbackend.kitaGruppe.KitaGruppeRepository;
import com.example.kitanotbetreuungbackend.user.User;
import com.example.kitanotbetreuungbackend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final KitaRepository kitaRepository;
    private final KitaGruppeRepository kitaGruppeRepository;
    private final KindRepository kindRepository;

    @Autowired
    public DataLoader(UserRepository userRepository, KitaRepository kitaRepository,
                      KitaGruppeRepository kitaGruppeRepository, KindRepository kindRepository) {
        this.userRepository = userRepository;
        this.kitaRepository = kitaRepository;
        this.kitaGruppeRepository = kitaGruppeRepository;
        this.kindRepository = kindRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        // Create Kita
        Kita kita = new Kita("RegenbogenKita", new ArrayList<>());
        kitaRepository.save(kita);

        // Create KitaGruppe
        KitaGruppe kitaGruppe = new KitaGruppe("Die Löwen", new ArrayList<>());
        kitaGruppeRepository.save(kitaGruppe);

        // Link KitaGruppe to Kita
        kita.getKitaGruppen().add(kitaGruppe);
        kitaRepository.save(kita);

        // Create Users (Eltern) and Kinder
        for (int i = 0; i < 10; i++) {
            User eltern = new User(new ArrayList<>(), "Eltern" + i, kita, "password");
            userRepository.save(eltern);

            Kind kind = new Kind("Vorname" + i, "Nachname" + i, eltern, kitaGruppe);
            kindRepository.save(kind);

        }

        // Optional: Create admin user
        User admin = new User(new ArrayList<>(), "admin", kita, "12345");
        admin.setAdmin(true);
        userRepository.save(admin);

        Kind kind = new Kind("Bahadir", "Erbek", admin , kitaGruppe);
        kindRepository.save(kind);
    }
}
