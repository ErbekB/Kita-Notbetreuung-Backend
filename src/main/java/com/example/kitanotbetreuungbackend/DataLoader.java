package com.example.kitanotbetreuungbackend;

import com.example.kitanotbetreuungbackend.kind.Kind;
import com.example.kitanotbetreuungbackend.kind.KindRepository;
import com.example.kitanotbetreuungbackend.kita.Kita;
import com.example.kitanotbetreuungbackend.kita.KitaRepository;
import com.example.kitanotbetreuungbackend.kitaGruppe.KitaGruppe;
import com.example.kitanotbetreuungbackend.kitaGruppe.KitaGruppeRepository;
import com.example.kitanotbetreuungbackend.kitaGruppe.Verlauf;
import com.example.kitanotbetreuungbackend.user.User;
import com.example.kitanotbetreuungbackend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
        Kita kita = new Kita("Märchenwald Kinderhaus", new ArrayList<>());
        kitaRepository.save(kita);

        // Create KitaGruppe
        KitaGruppe kitaGruppe = new KitaGruppe("Die Regenbogenfische", new ArrayList<>());
        kitaGruppeRepository.save(kitaGruppe);

        kitaGruppe.setKita(kita);
        kitaGruppeRepository.save(kitaGruppe);

        // Link KitaGruppe to Kita
        kita.getKitaGruppen().add(kitaGruppe);
        kitaRepository.save(kita);

        // Create Users (Eltern) and Kinder
        User eltern = new User(new ArrayList<>(), "Lisa", kita, "lisa");
        userRepository.save(eltern);

        Kind kind = new Kind("Emma", "Müller", eltern, kitaGruppe);
        kind.setCounter(5);
        kindRepository.save(kind);

        User eltern2 = new User(new ArrayList<>(), "Jan", kita, "jan");
        userRepository.save(eltern2);

        Kind kind2 = new Kind("Noah", "Schmidt", eltern2, kitaGruppe);
        kind2.setCounter(4);
        kindRepository.save(kind2);

        User eltern3 = new User(new ArrayList<>(), "Anna", kita, "anna");
        userRepository.save(eltern3);

        Kind kind3 = new Kind("Leon", "Wagner", eltern3, kitaGruppe);
        kind3.setCounter(4);
        kindRepository.save(kind3);

        User eltern4 = new User(new ArrayList<>(), "Julian", kita, "julian");
        userRepository.save(eltern4);

        Kind kind4 = new Kind("Emil", "Soeparwata", eltern4, kitaGruppe);
        kind4.setCounter(1);
        kindRepository.save(kind4);

        Kind kind24 = new Kind("Milo", "Soeparwata", eltern4, kitaGruppe);
        kind24.setCounter(3);
        kindRepository.save(kind24);

        User eltern5 = new User(new ArrayList<>(), "Sarah", kita, "sarah");
        userRepository.save(eltern5);

        Kind kind5 = new Kind("Mia", "Hoffmann", eltern5, kitaGruppe);
        kind5.setCounter(4);
        kindRepository.save(kind5);

        User eltern6 = new User(new ArrayList<>(), "Timo", kita, "timo");
        userRepository.save(eltern6);

        Kind kind6 = new Kind("Luca", "Richter", eltern6, kitaGruppe);
        kind6.setCounter(2);
        kind6.setTeilnahmeNotbetreuung(true);
        kindRepository.save(kind6);

        User eltern7 = new User(new ArrayList<>(), "Bahadir", kita, "bahadir");
        eltern7.setAdmin(true);
        userRepository.save(eltern7);

        Kind kind7 = new Kind("Otto", "Erbek", eltern7, kitaGruppe);
        kind7.setCounter(1);
        kind7.setTeilnahmeNotbetreuung(true);
        kindRepository.save(kind7);

        User eltern8 = new User(new ArrayList<>(), "Julia", kita, "julia");
        userRepository.save(eltern8);

        Kind kind8 = new Kind("Hannah", "Schuster", eltern8, kitaGruppe);
        kind8.setCounter(3);
        kind8.setTeilnahmeNotbetreuung(true);
        kindRepository.save(kind8);

        User eltern9 = new User(new ArrayList<>(), "Michaela", kita, "michaela");
        userRepository.save(eltern9);

        Kind kind9 = new Kind("Finn", "Köhler", eltern9, kitaGruppe);
        kind9.setCounter(6);
        kindRepository.save(kind9);

        User eltern10 = new User(new ArrayList<>(), "Alexander", kita, "alexander");
        userRepository.save(eltern10);

        Kind kind10 = new Kind("Lina", "Meier", eltern10, kitaGruppe);
        kind10.setCounter(6);
        kindRepository.save(kind10);

        User eltern11 = new User(new ArrayList<>(), "Stefanie", kita, "stefanie");
        userRepository.save(eltern11);

        Kind kind11 = new Kind("Elias", "Schulz", eltern11, kitaGruppe);
        kind11.setCounter(4);
        kindRepository.save(kind11);

        User eltern12 = new User(new ArrayList<>(), "Fatih", kita, "fatih");
        userRepository.save(eltern12);

        Kind kind12 = new Kind("Elif", "Baskurt", eltern12, kitaGruppe);
        kind12.setCounter(1);
        kind12.setTeilnahmeNotbetreuung(true);
        kindRepository.save(kind12);


        // Optional: Create admin user
        User admin = new User(new ArrayList<>(), "Fajul", kita, "fajul");
        userRepository.save(admin);

        Kind kind13 = new Kind("Emel", "Agatha", admin, kitaGruppe);
        kind13.setCounter(3);
        kindRepository.save(kind13);

        kitaGruppe.setAdmin(eltern7);
        kitaGruppeRepository.save(kitaGruppe);

        //Historie wird erstellt
        List<Kind> kindListe = new ArrayList<>();
        kindListe.add(kind);
        kindListe.add(kind3);
        kindListe.add(kind10);
        kindListe.add(kind24);
        kindListe.add(kind8);

        Verlauf verlauf = new Verlauf(kindListe, LocalDate.of(2024,1,5));
        kitaGruppe.addVerlauf(verlauf);
        kitaGruppeRepository.save(kitaGruppe);


        List<Kind> kindListe2 = new ArrayList<>();
        kindListe2.add(kind10);
        kindListe2.add(kind24);
        kindListe2.add(kind3);
        kindListe2.add(kind13);
        kindListe2.add(kind);

        Verlauf verlauf2 = new Verlauf(kindListe2, LocalDate.of(2024,1,3));
        kitaGruppe.addVerlauf(verlauf2);
        kitaGruppeRepository.save(kitaGruppe);


        List<Kind> kindListe3 = new ArrayList<>();
        kindListe3.add(kind);
        kindListe3.add(kind10);
        kindListe3.add(kind11);
        kindListe3.add(kind2);
        kindListe3.add(kind4);

        Verlauf verlauf3 = new Verlauf(kindListe3, LocalDate.of(2024,1,2));
        kitaGruppe.addVerlauf(verlauf3);
        kitaGruppeRepository.save(kitaGruppe);


        List<Kind> kindListe4 = new ArrayList<>();
        kindListe4.add(kind);
        kindListe4.add(kind5);
        kindListe4.add(kind3);
        kindListe4.add(kind13);
        kindListe4.add(kind10);

        Verlauf verlauf4 = new Verlauf(kindListe4, LocalDate.of(2023,12,21));
        kitaGruppe.addVerlauf(verlauf4);
        kitaGruppeRepository.save(kitaGruppe);


        List<Kind> kindListe5 = new ArrayList<>();
        kindListe5.add(kind2);
        kindListe5.add(kind11);
        kindListe5.add(kind5);
        kindListe5.add(kind10);
        kindListe5.add(kind8);

        Verlauf verlauf5 = new Verlauf(kindListe5, LocalDate.of(2023,12,20));
        kitaGruppe.addVerlauf(verlauf5);
        kitaGruppeRepository.save(kitaGruppe);

        List<Kind> kindListe6 = new ArrayList<>();
        kindListe6.add(kind13);
        kindListe6.add(kind5);
        kindListe6.add(kind3);
        kindListe6.add(kind);
        kindListe6.add(kind24);


        Verlauf verlauf6 = new Verlauf(kindListe6, LocalDate.of(2023, 12, 19));
        kitaGruppe.addVerlauf(verlauf6);
        kitaGruppeRepository.save(kitaGruppe);

        List<Kind> kindListe7 = new ArrayList<>();
        kindListe7.add(kind2);
        kindListe7.add(kind11);
        kindListe7.add(kind5);
        kindListe7.add(kind6);
        kindListe7.add(kind10);

        Verlauf verlauf7 = new Verlauf(kindListe7, LocalDate.of(2023, 12, 11));
        kitaGruppe.addVerlauf(verlauf7);
        kitaGruppeRepository.save(kitaGruppe);
    }
}