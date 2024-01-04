package com.example.kitanotbetreuungbackend.user;

import com.example.kitanotbetreuungbackend.kind.Kind;
import com.example.kitanotbetreuungbackend.kita.Kita;
import com.example.kitanotbetreuungbackend.kita.KitaRepository;
import com.example.kitanotbetreuungbackend.kitaGruppe.KitaGruppe;
import com.example.kitanotbetreuungbackend.kitaGruppe.KitaGruppeRepository;
import com.example.kitanotbetreuungbackend.session.RegistrierenRequestDTO;
import com.example.kitanotbetreuungbackend.session.Session;
import com.example.kitanotbetreuungbackend.session.SessionRepository;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class UserController {

    private final KitaRepository kitaRepository;
    private final UserRepository userRepository;
    private final KitaGruppeRepository kitaGruppeRepository;

    @Autowired
    public UserController(KitaRepository kitaRepository, UserRepository userRepository, KitaGruppeRepository kitaGruppeRepository) {
        this.kitaRepository = kitaRepository;
        this.userRepository = userRepository;
        this.kitaGruppeRepository = kitaGruppeRepository;
    }

    @GetMapping("/user")
    public ResponseEntity<UserDTO> user(@ModelAttribute("sessionUser") Optional<User> sessionUserOptional) {
        User sessionUser = getUserFromSession(sessionUserOptional);
        UserDTO userDTO = new UserDTO(sessionUser.getName(), sessionUser.isAdmin());
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/index")
    public ResponseEntity<IndexDTO> hauptseite(@ModelAttribute("sessionUser") Optional<User> sessionUserOptional) {
        User sessionUser = getUserFromSession(sessionUserOptional);

        boolean admin = sessionUser.isAdmin();
        boolean notbetreuung = sessionUser.getKita().isNotbetreuung();
        List<Kind> kinderListe;
        String kitaName = sessionUser.getKita().getName();
        String kitaGruppeName;

        if (!sessionUser.getKind().isEmpty()) {
            Kind kind = sessionUser.getKind().get(0);
            KitaGruppe kitaGruppe = kind.getKitaGruppe();

            kitaGruppeName = kitaGruppe != null ? kitaGruppe.getName() : sessionUser.getKita().getKitaGruppen().get(0).getName();
            kinderListe = kitaGruppe != null ? kitaGruppe.getKinder() : new ArrayList<>();
        } else {
            kitaGruppeName = sessionUser.getKita().getKitaGruppen().get(0).getName();
            kinderListe = new ArrayList<>();
        }

        IndexDTO indexDTO = new IndexDTO(admin, notbetreuung, kinderListe, kitaName, kitaGruppeName);
        return ResponseEntity.ok(indexDTO);
    }


    @PostMapping("/index")
    public ResponseEntity<?> statusNotbetreuung(@ModelAttribute("sessionUser") Optional<User> sessionUserOptional) {
        User sessionUser = getUserFromSession(sessionUserOptional);
        checkIfUserIsAdmin(sessionUser);

        Kita sessionUsersKita = sessionUser.getKita();
        sessionUsersKita.setNotbetreuung(!sessionUsersKita.isNotbetreuung());
        kitaRepository.save(sessionUsersKita);

        return ResponseEntity.ok("Die Notbetreuung wurde umgeschaltet");
    }


    @PostMapping("/registrieren")
    public ResponseEntity<?> registerUser(@RequestBody RegistrierenRequestDTO registrieren) {
        String name = registrieren.getName();
        String passwort = registrieren.getPasswort();
        String kitaName = registrieren.getKita().toLowerCase();
        int postleitzahl = Integer.parseInt(registrieren.getPostleitzahl());
        String kitaGruppeName = registrieren.getKitaGruppe().toLowerCase();

        // Validierungen
        if (name.length() < 3) {
            return ResponseEntity.badRequest().body("Name muss mindestens 3 Zeichen lang sein");
        }

        if (passwort.length() < 5) {
            return ResponseEntity.badRequest().body("Passwort muss mindestens 5 Zeichen lang sein");
        }

        if (postleitzahl > 99999 || postleitzahl < 1067) {
            return ResponseEntity.badRequest().body("Keine gültige Postleitzahl");
        }

        // Kita suchen oder erstellen
        Kita kita = kitaRepository.findByNameAndPostleitzahl(kitaName, postleitzahl)
                .orElseGet(() -> {
                    Kita neueKita = new Kita();
                    neueKita.setName(kitaName);
                    neueKita.setPostleitzahl(postleitzahl);
                    return neueKita;
                });
        kitaRepository.save(kita);

        // Prüfen, ob die Kitagruppe bereits existiert und ob sie einen Admin hat
        Optional<KitaGruppe> existierendeGruppe = kitaGruppeRepository.findByNameAndKita(kitaGruppeName, kita);
        if (existierendeGruppe.isPresent() && existierendeGruppe.get().getAdmin() != null) {
            return ResponseEntity
                    .badRequest()
                    .body("Die Kitagruppe " + kitaGruppeName + " hat bereits einen Admin: " + existierendeGruppe.get().getAdmin().getName());
        }

        // Neue Kitagruppe erstellen und speichern
        KitaGruppe kitaGruppe = existierendeGruppe.orElseGet(() -> {
            KitaGruppe neueGruppe = new KitaGruppe();
            neueGruppe.setName(kitaGruppeName);
            neueGruppe.setKita(kita);
            return neueGruppe;
        });
        kitaGruppeRepository.save(kitaGruppe);

        // Neuen Benutzer erstellen und speichern
        User newUser = new User();
        newUser.setName(name);
        newUser.setPasswort(passwort);
        newUser.setAdmin(true);
        newUser.setKita(kita);
        userRepository.save(newUser);

        // Kitagruppe mit dem neuen Admin aktualisieren
        kitaGruppe.setAdmin(newUser);
        kitaGruppeRepository.save(kitaGruppe);

        return ResponseEntity.ok().body(newUser);
    }

    @DeleteMapping("/userloeschen")
    public ResponseEntity<?> userLoeschen(@ModelAttribute("sessionUser") Optional<User> sessionUserOptional) {
        User user = getUserFromSession(sessionUserOptional);

        userRepository.delete(user);
        return ResponseEntity.noContent().build();  // Rückgabe von 204 No Content für erfolgreiche Löschung
    }

    @PostMapping("/abc")
    public ResponseEntity<?> namenAendern(@RequestBody UserNamenAendernDTO userName, @ModelAttribute("sessionUser") Optional<User> sessionUserOptional) {
        User user = getUserFromSession(sessionUserOptional);

        String neuerName = userName.getUsername();
        if (neuerName == null || neuerName.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Der neue Name darf nicht leer sein.");
        }

        user.setName(neuerName);
        userRepository.save(user);
        return ResponseEntity.ok("Benutzername erfolgreich geändert.");
    }


    @PostMapping("/passwortAendern")
    public ResponseEntity<?> passwortAendern(@RequestBody UserPasswortAendernDTO userPasswort, @ModelAttribute("sessionUser") Optional<User> sessionUserOptional) {
        User user = getUserFromSession(sessionUserOptional);

        String neuesPasswort = userPasswort.getPasswort();
        if (neuesPasswort == null || neuesPasswort.length() < 5) {
            return ResponseEntity.badRequest().body("Das Passwort muss mindestens 5 Zeichen lang sein.");
        }

        user.setPasswort(neuesPasswort);

        userRepository.save(user);
        return ResponseEntity.ok("Passwort erfolgreich geändert.");
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
