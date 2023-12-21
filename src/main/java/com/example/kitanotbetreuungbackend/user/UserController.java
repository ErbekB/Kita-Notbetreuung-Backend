package com.example.kitanotbetreuungbackend.user;

import com.example.kitanotbetreuungbackend.kind.Kind;
import com.example.kitanotbetreuungbackend.kita.Kita;
import com.example.kitanotbetreuungbackend.kita.KitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private KitaRepository kitaRepository;
    private UserRepository userRepository;

    @Autowired
    public UserController(KitaRepository kitaRepository, UserRepository userRepository) {
        this.kitaRepository = kitaRepository;
        this.userRepository = userRepository;
    }


    @GetMapping("/index/{id}")
    public IndexDTO hauptseite (@PathVariable long id) {

        if (userRepository.existsById(id)) {
            User Benutzer = userRepository.findById(id).get();
            boolean Admin = Benutzer.isAdmin();
            boolean Notbetreuung = Benutzer.getKita().isNotbetreuung();
            List<Kind> kinderListe = Benutzer.getKind().get(0).getKitaGruppe().getKinder(); //TODO: Zurzeit wird nur das erste Kind ausgewählt.

            return new IndexDTO(Admin, Notbetreuung, kinderListe);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Benutzer nicht gefunden");
    }

    @PostMapping("/index/{id}")
    public Kita statusNotbetreuung(@PathVariable long id){

        if (userRepository.existsById(id)) {
            User Benutzer = userRepository.findById(id).get();
            if(Benutzer.isAdmin()) {
                Benutzer.getKita().setNotbetreuung(true);
                userRepository.save(Benutzer);
                return Benutzer.getKita();
            }
        }
        return null;
    }
}
