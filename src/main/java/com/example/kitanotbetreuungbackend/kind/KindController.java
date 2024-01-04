package com.example.kitanotbetreuungbackend.kind;

import com.example.kitanotbetreuungbackend.kitaGruppe.KitaGruppe;
import com.example.kitanotbetreuungbackend.kitaGruppe.KitaGruppeRepository;
import com.example.kitanotbetreuungbackend.kitaGruppe.StatusNotfallbetreuungDTO;
import com.example.kitanotbetreuungbackend.user.User;
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
    private final KindRepository kindRepository;
    private final KitaGruppeRepository kitaGruppeRepository;


    @Autowired
    public KindController(KindRepository kindRepository, KitaGruppeRepository kitaGruppeRepository) {
        this.kindRepository = kindRepository;
        this.kitaGruppeRepository = kitaGruppeRepository;
    }

    @GetMapping("/notfall")
    public KitaGruppeDTO uebersicht(@ModelAttribute("sessionUser") Optional<User> sessionUserOptional) {
        User user = getUserFromSession(sessionUserOptional);

        if (user.getKind().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Admin muss sein Kind hinzufügen");
        }

        List<Kind> kinder = user.getKind().get(0).getKitaGruppe().getKinder();
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

        boolean teilnahme = user.getKind().get(0).isTeilnahmeNotbetreuung();
        boolean notbetreuungNichtNotwendig = user.getKind().get(0).isNotbetreuungNichtNotwendig();
        boolean statusNotbetreuung = user.getKita().isNotbetreuung();

        return new KitaGruppeDTO(kinderDTOs, teilnahme, user.getId(), notbetreuungNichtNotwendig, statusNotbetreuung);
    }

    @GetMapping("/notfall/notbetreuung")
    public ResponseEntity<StatusNotfallbetreuungDTO> statusAbstimmungNotbetreuung(@ModelAttribute("sessionUser") Optional<User> sessionUserOptional) {
        User user = getUserFromSession(sessionUserOptional);
        checkIfUserIsAdmin(user);

        if (user.getKind().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin hat kein Kind.");
        }

        KitaGruppe kitaGruppe = user.getKind().get(0).getKitaGruppe();

        StatusNotfallbetreuungDTO status = new StatusNotfallbetreuungDTO(kitaGruppe.isAbstimmungAbgeschlossen());
        return ResponseEntity.ok(status);

    }

    @PostMapping("/notfall/notbetreuung")
    public ResponseEntity<StatusNotfallbetreuungDTO> statusAbstimmungNotbetreuungÄndern(@ModelAttribute("sessionUser") Optional<User> sessionUserOptional) {
        User user = getUserFromSession(sessionUserOptional);
        checkIfUserIsAdmin(user);

        if (user.getKind().isEmpty() || user.getKind().get(0).getKitaGruppe() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin hat kein Kind in einer KitaGruppe.");
        }

        KitaGruppe kitaGruppe = user.getKind().get(0).getKitaGruppe();
        kitaGruppe.setAbstimmungAbgeschlossen(true);
        kitaGruppeRepository.save(kitaGruppe);

        StatusNotfallbetreuungDTO status = new StatusNotfallbetreuungDTO(kitaGruppe.isAbstimmungAbgeschlossen());
        return ResponseEntity.ok(status);
    }



    @PostMapping("/notfall/{kindId}")
    public ResponseEntity<?> bearbeitung(@PathVariable long kindId, @ModelAttribute("sessionUser") Optional<User> sessionUserOptional) {
        User sessionUser = getUserFromSession(sessionUserOptional);

        Kind kind = kindRepository.findById(kindId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kind nicht gefunden"));

        if (!kind.getUser().getId().equals(sessionUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Nicht berechtigt, die Teilnahme für dieses Kind zu ändern");
        }

        // Aktualisierung der Teilnahme und des Counters
        kind.setTeilnahmeNotbetreuung(!kind.isTeilnahmeNotbetreuung());
        kind.setCounter(kind.getCounter() + 1);
        kindRepository.save(kind);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/notfall/aendern/{kindId}")
    public ResponseEntity<?> aendern(@PathVariable long kindId, @ModelAttribute("sessionUser") Optional<User> sessionUserOptional) {
        User sessionUser = getUserFromSession(sessionUserOptional);

        Kind kind = kindRepository.findById(kindId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kind nicht gefunden"));

        // Überprüfen, ob das Kind dem eingeloggten User gehört
        if (!kind.getUser().getId().equals(sessionUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Nicht berechtigt, die Teilnahme für dieses Kind zu ändern");
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
    public ResponseEntity<?> keineTeilnahme(@PathVariable long kindId, @ModelAttribute("sessionUser") Optional<User> sessionUserOptional) {
        User sessionUser = getUserFromSession(sessionUserOptional);

        Kind kind = kindRepository.findById(kindId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kind nicht gefunden."));

        if (!kind.getUser().getId().equals(sessionUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Nicht berechtigt, diese Änderung für dieses Kind durchzuführen.");
        }

        kind.setNotbetreuungNichtNotwendig(true);
        kindRepository.save(kind);

        return ResponseEntity.ok("Die Teilnahme des Kindes an der Notbetreuung wurde erfolgreich als nicht notwendig markiert.");
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void resetProperties() {
        List<Kind> kinder = kindRepository.findAll();
        kinder.forEach(kind -> {
            kind.setTeilnahmeNotbetreuung(false);
            kind.setNotbetreuungNichtNotwendig(false);
        });
        kindRepository.saveAll(kinder);

        List<KitaGruppe> kitaGruppen = kitaGruppeRepository.findAll();
        for (KitaGruppe kitaGruppe : kitaGruppen) {
            kitaGruppe.setAbstimmungAbgeschlossen(false);
        }
        kitaGruppeRepository.saveAll(kitaGruppen);
    }

    private void checkIfUserIsAdmin(User user) {
        if (!user.isAdmin()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Nur Admins dürfen diese Aktion ausführen.");
        }
    }

    private User getUserFromSession(Optional<User> sessionUserOptional) {
        return sessionUserOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No valid login"));
    }
}


