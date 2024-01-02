package com.example.kitanotbetreuungbackend.user;

import com.example.kitanotbetreuungbackend.kind.Kind;
import com.example.kitanotbetreuungbackend.kita.Kita;
import com.example.kitanotbetreuungbackend.kita.KitaRepository;
import com.example.kitanotbetreuungbackend.kitaGruppe.KitaGruppe;
import com.example.kitanotbetreuungbackend.kitaGruppe.KitaGruppeRepository;
import com.example.kitanotbetreuungbackend.session.LoginRequestDTO;
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

    private KitaRepository kitaRepository;
    private UserRepository userRepository;
    private KitaGruppeRepository kitaGruppeRepository;
    private SessionRepository sessionRepository;

    @Autowired
    public UserController(KitaRepository kitaRepository, UserRepository userRepository, KitaGruppeRepository kitaGruppeRepository, SessionRepository sessionRepository) {
        this.kitaRepository = kitaRepository;
        this.userRepository = userRepository;
        this.kitaGruppeRepository = kitaGruppeRepository;
        this.sessionRepository = sessionRepository;
    }

    @GetMapping("/user")
    public UserDTO user(@ModelAttribute("sessionUser") Optional<User> sessionUserOptional) {
        User sessionUser = sessionUserOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No valid login"));
        return new UserDTO(sessionUser.getName(), sessionUser.isAdmin());
    }


    @GetMapping("/index")
    public IndexDTO hauptseite(@ModelAttribute("sessionUser") Optional<User> sessionUserOptional) {
        User sessionUser = sessionUserOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Benutzer nicht gefunden"));

        boolean admin = sessionUser.isAdmin();
        boolean notbetreuung = sessionUser.getKita().isNotbetreuung();
        List<Kind> kinderListe = new ArrayList<>();

        if (!sessionUser.getKind().isEmpty()) {
            kinderListe = sessionUser.getKind().get(0).getKitaGruppe().getKinder();
        }
        return new IndexDTO(admin, notbetreuung, kinderListe);
    }

    @GetMapping("/index/notbetreuung")
    public Kita statusNotbetreuung(@PathVariable long id) {
        if (userRepository.existsById(id)) {
            User Benutzer = userRepository.findById(id).get();
            if (Benutzer.isAdmin()) {
                Benutzer.getKita().setNotbetreuung(!Benutzer.getKita().isNotbetreuung());
                userRepository.save(Benutzer);
                return Benutzer.getKita();
            }
        }
        return null;
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

        return ResponseEntity.ok(newUser);
    }
    @DeleteMapping("/userloeschen")
    public User userLoeschen(@ModelAttribute("sessionUser")Optional<User> sessionUser) {
        User user = sessionUser
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Benutzer nicht gefunden"));

        long test = user.getId();

        System.out.println(test);
       userRepository.delete(user);
        return null;
    }

    @PostMapping("/abc")
    public User namenAendern(@RequestBody UserNamenAendernDTO userName, @ModelAttribute("sessionUser") Optional<User> user1){
            User user = user1
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Benutzer nicht gefunden"));
            user.setName(userName.getUsername());
        userRepository.save(user);
        return null;
    }

    @PostMapping("/passwortAendern")
    public User passwortAendern(@RequestBody UserPasswortAendernDTO userPasswort, @ModelAttribute("sessionUser") Optional<User> sessionUser){
        User user = sessionUser
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Benutzer nicht gefunden"));
        user.setPasswort(userPasswort.getPasswort());
        userRepository.save(user);
        return null;
    }
}
