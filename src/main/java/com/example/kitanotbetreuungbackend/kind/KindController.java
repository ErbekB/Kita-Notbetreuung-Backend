package com.example.kitanotbetreuungbackend.kind;

import com.example.kitanotbetreuungbackend.kita.KitaRepository;
import com.example.kitanotbetreuungbackend.user.User;
import com.example.kitanotbetreuungbackend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class KindController {
    private KindRepository kindRepository;
    private KitaRepository kitaRepository;
    private UserRepository userRepository;

    @Autowired
    public KindController(KindRepository kindRepository, KitaRepository kitaRepository, UserRepository userRepository) {
        this.kindRepository = kindRepository;
        this.kitaRepository = kitaRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/notfall")
    public KitaGruppeDTO uebersicht(@ModelAttribute("sessionUser") Optional<User> sessionUserOptional) {
        User sessionUser = sessionUserOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No valid login"));

        if (sessionUser.getKind().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Admin muss sein Kind hinzufügen");
        }

        List<Kind> kinder = sessionUser.getKind().get(0).getKitaGruppe().getKinder();
        List<KindDTO> kinderDTOs = kinder.stream()
                .filter(kind -> !kind.isNotbetreuungNichtNotwendig())
                .map(kind -> new KindDTO(
                        kind.getId(),
                        kind.getVorname(),
                        kind.getNachname(),
                        kind.getCounter(),
                        kind.getUser().getId(),
                        kind.isTeilnahmeNotbetreuung(),
                        kind.isNotbetreuungNichtNotwendig()))
                .collect(Collectors.toList());

        boolean teilnahme = sessionUser.getKind().get(0).isTeilnahmeNotbetreuung();
        boolean notbetreuungNichtNotwendig = sessionUser.getKind().get(0).isNotbetreuungNichtNotwendig();
        boolean statusNotbetreuung = sessionUser.getKita().isNotbetreuung();

        return new KitaGruppeDTO(kinderDTOs, teilnahme, sessionUser.getId(), notbetreuungNichtNotwendig, statusNotbetreuung);
    }



    @PostMapping("/notfall/{kindId}") //Todo return data to check the counter
    public ResponseEntity<?> bearbeitung(@PathVariable long kindId, @ModelAttribute("sessionUser") User sessionUser) {
        Optional<Kind> kindOptional = kindRepository.findById(kindId);

        if (kindOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Kind nicht gefunden");
        }

        Kind kind = kindOptional.get();

        // Überprüfen, ob das Kind dem eingeloggten User gehört
        if (!kind.getUser().getId().equals(sessionUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Nicht berechtigt, die Teilnahme für dieses Kind zu ändern");
        }

        // Aktualisierung der Teilnahme und des Counters
        kind.setTeilnahmeNotbetreuung(!kind.isTeilnahmeNotbetreuung());
        kind.setCounter(kind.getCounter() + 1);
        kindRepository.save(kind);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/notfall/aendern/{kindId}")
    public ResponseEntity<?> aendern(@PathVariable long kindId, @ModelAttribute("sessionUser") User sessionUser) {
        Optional<Kind> kindOptional = kindRepository.findById(kindId);

        if (kindOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Kind nicht gefunden");
        }

        Kind kind = kindOptional.get();

        // Überprüfen, ob das Kind dem eingeloggten User gehört
        if (!kind.getUser().getId().equals(sessionUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Nicht berechtigt, die Teilnahme für dieses Kind zu ändern");
        }

        // Aktualisierung der Teilnahme und des Counters
        kind.setTeilnahmeNotbetreuung(!kind.isTeilnahmeNotbetreuung());
        if (kind.isTeilnahmeNotbetreuung()) {
            kind.setCounter(kind.getCounter() + 1); // Erhöhen, wenn das Kind jetzt teilnimmt
        } else {
            kind.setCounter(kind.getCounter() > 0 ? kind.getCounter() - 1 : 0); // Verringern, aber nicht unter 0
        }
        kindRepository.save(kind);

        return ResponseEntity.ok().build();
    }


    //Teilnahme zurückziehen
    @PostMapping("/notfall/teilnahme/{kindId}")
    public ResponseEntity<?> keineTeilnahme(@PathVariable long kindId, @ModelAttribute("sessionUser") User sessionUser) {
        Optional<Kind> kindOptional = kindRepository.findById(kindId);

        if (kindOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kind nicht gefunden.");
        }

        Kind kind = kindOptional.get();

        // Überprüfen, ob das Kind dem eingeloggten User gehört
        if (!kind.getUser().getId().equals(sessionUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Nicht berechtigt, diese Änderung für dieses Kind durchzuführen.");
        }

        kind.setNotbetreuungNichtNotwendig(true);
        kindRepository.save(kind);

        return ResponseEntity.ok("Die Teilnahme des Kindes an der Notbetreuung wurde erfolgreich als nicht notwendig markiert.");
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void resetProperties(){
        List<Kind> kinder = kindRepository.findAll();
        for (Kind kind : kinder) {
            kind.setTeilnahmeNotbetreuung(false);
            kind.setNotbetreuungNichtNotwendig(false);
            kindRepository.save(kind);
        }
        }
    }


