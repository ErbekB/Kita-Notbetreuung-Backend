package com.example.kitanotbetreuungbackend.kitaGruppe;

import com.example.kitanotbetreuungbackend.kind.Kind;
import com.example.kitanotbetreuungbackend.user.User;
import com.example.kitanotbetreuungbackend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class VerlaufController {

    private KitaGruppeRepository kitaGruppeRepository;
    private UserRepository userRepository;

    @Autowired
    public VerlaufController(KitaGruppeRepository kitaGruppeRepository, UserRepository userRepository) {
        this.kitaGruppeRepository = kitaGruppeRepository;
        this.userRepository = userRepository;
    }
    @GetMapping("/verlauf")
    public VerlaufDTO teilnahmeHistorie(@ModelAttribute("sessionUser") User user){
       List<Verlauf> verlauf = user.getKind().get(0).getKitaGruppe().getVerläufe();
       return new VerlaufDTO(verlauf);
    }


    @PostMapping("/verlauf/speichern")
    public ResponseEntity<?> speichern(@ModelAttribute("sessionUser") User user) {

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
}
