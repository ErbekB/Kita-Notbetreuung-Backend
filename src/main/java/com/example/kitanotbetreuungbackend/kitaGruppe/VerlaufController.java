package com.example.kitanotbetreuungbackend.kitaGruppe;

import com.example.kitanotbetreuungbackend.kind.Kind;
import com.example.kitanotbetreuungbackend.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class VerlaufController {

    private final KitaGruppeRepository kitaGruppeRepository;

    @Autowired
    public VerlaufController(KitaGruppeRepository kitaGruppeRepository) {
        this.kitaGruppeRepository = kitaGruppeRepository;
    }

    @GetMapping("/verlauf")
    public ResponseEntity<VerlaufDTO> teilnahmeHistorie(@ModelAttribute("sessionUser") Optional<User> sessionUserOptional) {
        User user = getUserFromSession(sessionUserOptional);

        if (user.getKind().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Benutzer hat keine Kinder zugeordnet.");
        }

        List<Verlauf> verlauf = user.getKind().get(0).getKitaGruppe().getVerläufe();
        VerlaufDTO verlaufDTO = new VerlaufDTO(verlauf);
        return ResponseEntity.ok(verlaufDTO);
    }

    @PostMapping("/verlauf/speichern")
    public ResponseEntity<?> speichern(@ModelAttribute("sessionUser") Optional<User> sessionUserOptional) {
        User user = getUserFromSession(sessionUserOptional);

        if (!user.isAdmin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nur Admins können diese Funktion nutzen.");
        }

        if (user.getKind().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Admin benötigt Kind.");
        }

        List<Kind> teilnehmend = new ArrayList<>();
        List<Kind> kinder = user.getKind().get(0).getKitaGruppe().getKinder();
        for (Kind kind : kinder ) {
            if (kind.isTeilnahmeNotbetreuung()){
                teilnehmend.add(kind);
            }
        }

        Verlauf neuerVerlauf = new Verlauf(teilnehmend);
        KitaGruppe kitaGruppe = user.getKind().get(0).getKitaGruppe();
        kitaGruppe.addVerlauf(neuerVerlauf);
        kitaGruppeRepository.save(kitaGruppe);
        return ResponseEntity.ok().body("Verlauf hinzugefügt");
    }

    private User getUserFromSession(Optional<User> sessionUserOptional) {
        return sessionUserOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No valid login"));
    }
}
