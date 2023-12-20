package com.example.kitanotbetreuungbackend.user;

import com.example.kitanotbetreuungbackend.kind.Kind;
import com.example.kitanotbetreuungbackend.kita.KitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
public class UserController {

    private KitaRepository kitaRepository;
    private UserRepository userRepository;

    @Autowired
    public UserController(KitaRepository kitaRepository, UserRepository userRepository) {
        this.kitaRepository = kitaRepository;
        this.userRepository = userRepository;
    }


    @GetMapping("/index/{id}")
    public IndexDTO hauptseite (@RequestParam long id) {

        if (userRepository.existsById(id)) {
            User Benutzer = userRepository.findById(id).get();
            boolean Admin = Benutzer.isAdmin();
            boolean Notbetreuung = Benutzer.getKita().isNotbetreuung();
            ArrayList<Kind> kinderListe = Benutzer.getKind().get(1).getKitaGruppe().getKinder(); //TODO: Zurzeit wird nur das erste Kind ausgewählt.

            return new IndexDTO(Admin, Notbetreuung, kinderListe);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Benutzer nicht gefunden");
    }

    @PostMapping("/index/{id}")
    public StatusDTO statusNotbetreuung(@RequestParam long id){
        return new StatusDTO();
    }



}
