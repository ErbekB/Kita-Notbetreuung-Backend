package com.example.kitanotbetreuungbackend.user;

import com.example.kitanotbetreuungbackend.kind.Kind;
import com.example.kitanotbetreuungbackend.kind.KindRepository;
import com.example.kitanotbetreuungbackend.kitaGruppe.KitaGruppe;
import com.example.kitanotbetreuungbackend.kitaGruppe.KitaGruppeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class AdminController {

    private KindRepository kindRepository;
    private UserRepository userRepository;
    private KitaGruppeRepository kitaGruppeRepository;

    @Autowired
    public AdminController(KindRepository kindRepository, UserRepository userRepository, KitaGruppeRepository kitaGruppeRepository) {
        this.kindRepository = kindRepository;
        this.userRepository = userRepository;
        this.kitaGruppeRepository = kitaGruppeRepository;
    }


    // Methode zum Abrufen aller Eltern mit ihren Kindern
    @GetMapping("/admin/eltern")
    public ResponseEntity<?> getParentsOfGroup(@ModelAttribute("sessionUser") User sessionUser) {
        if (sessionUser == null) {
            return null;
        }

        if (!sessionUser.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Nur Admins dürfen die Eltern abrufen.");
        }

        KitaGruppe adminKitagruppe = kitaGruppeRepository.findByAdmin(sessionUser);
        if (adminKitagruppe == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Admin ist keiner Kitagruppe zugeordnet.");
        }

        List<Kind> kinderInGruppe = kindRepository.findAllByKitaGruppe(adminKitagruppe);

        KitaGruppeElternDTO dto = new KitaGruppeElternDTO();
        dto.setKitaName(adminKitagruppe.getKita().getName());
        dto.setKitaGruppeName(adminKitagruppe.getName());

        List<KitaGruppeElternDTO.ElternKindDTO> elternListe = kinderInGruppe.stream()
                .map(Kind::getUser)
                .distinct()
                .map(user -> {
                    KitaGruppeElternDTO.ElternKindDTO elternDTO = new KitaGruppeElternDTO.ElternKindDTO();
                    elternDTO.setElternId(user.getId());
                    elternDTO.setElternName(user.getName());
                    elternDTO.setKinder(user.getKind().stream()
                            .map(kind -> new KitaGruppeElternDTO.ElternKindDTO.KindDTO(kind.getId(), kind.getVorname(), kind.getNachname(), kind.getCounter()))
                            .collect(Collectors.toList()));
                    return elternDTO;
                })
                .collect(Collectors.toList());

        dto.setElternMitKindern(elternListe);
        return ResponseEntity.ok(dto);
    }

    // Methode zum Hinzufügen eines neuen Elternteils mit Kindern
    @PostMapping("/admin/eltern")
    public ResponseEntity<?> addParent(@RequestBody ElternHinzufuegenDTO eltern, @ModelAttribute("sessionUser") User sessionUser) {
        if (!sessionUser.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Nur Admins dürfen Eltern hinzufügen.");
        }

        // Neuen User erstellen
        User newUser = new User();
        newUser.setName(eltern.getName());
        newUser.setPasswort("Hallo123");
        newUser.setAdmin(false);
        newUser.setKita(sessionUser.getKita());

        // Neues Kind erstellen
        Kind neuesKind = new Kind();
        neuesKind.setVorname(eltern.getKindVorname());
        neuesKind.setNachname(eltern.getKindNachname());
        neuesKind.setUser(newUser);
        neuesKind.setKitaGruppe(sessionUser.getKita().getKitaGruppen().get(0));

        // Kind und Elternteil speichern
        userRepository.save(newUser);
        kindRepository.save(neuesKind);

        newUser.addKind(neuesKind);
        userRepository.save(newUser);

        // Response erstellen
        ElternHinzufuegenResponseDTO response = new ElternHinzufuegenResponseDTO(newUser, neuesKind);

        return ResponseEntity.ok(response);
    }

    // Methode zum Löschen eines Elternteils
    @DeleteMapping("/admin/eltern/{parentId}")
    public ResponseEntity<?> deleteParent(@PathVariable Long parentId, @ModelAttribute("sessionUser") User sessionUser) {
        if (!sessionUser.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Nur Admins dürfen Eltern löschen.");
        }

        userRepository.deleteById(parentId);
        return ResponseEntity.ok().build();
    }

    // Methode zum Hinzufügen eines Kindes zu einem Elternteil
    @PostMapping("/admin/eltern/{elternId}")
    public ResponseEntity<?> addKindToParent(@PathVariable Long elternId, @RequestBody KindHinzufuegenDTO kindDTO, @ModelAttribute("sessionUser") User sessionUser) {
        if (!sessionUser.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Nur Admins dürfen Kinder hinzufügen.");
        }

        Optional<User> elternteil = userRepository.findById(elternId);
        if (elternteil.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Elternteil nicht gefunden.");
        }

        KitaGruppe adminKitagruppe = kitaGruppeRepository.findByAdmin(sessionUser);
        if (adminKitagruppe == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Admin ist keiner Kitagruppe zugeordnet.");
        }

        Kind neuesKind = new Kind();
        neuesKind.setVorname(kindDTO.getVorname());
        neuesKind.setNachname(kindDTO.getNachname());
        neuesKind.setUser(elternteil.get());
        neuesKind.setKitaGruppe(adminKitagruppe);

        kindRepository.save(neuesKind);

        return ResponseEntity.ok(new KindHinzufuegenResponseDTO(neuesKind));
    }


    // Methode zum Aktualisieren des Counters eines Kindes
//    @PutMapping("/admin/kind-counter/{kindId}")


}
