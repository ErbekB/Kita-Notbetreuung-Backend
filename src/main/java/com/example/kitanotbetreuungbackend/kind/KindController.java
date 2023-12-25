package com.example.kitanotbetreuungbackend.kind;

import com.example.kitanotbetreuungbackend.kita.KitaRepository;
import com.example.kitanotbetreuungbackend.user.User;
import com.example.kitanotbetreuungbackend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
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

    @GetMapping("/notfall/{id}")
    public KitaGruppeDTO uebersicht(@PathVariable long id) {
        if (userRepository.existsById(id)){
            User benutzer= userRepository.findById(id).get();
            List<Kind> KinderListe = benutzer.getKind().get(0).getKitaGruppe().getKinder();
            boolean teilnahme = benutzer.getKind().get(0).isTeilnahmeNotbetreuung();
            return new KitaGruppeDTO(KinderListe, teilnahme);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Benutzer nicht gefunden");
    }

    @PostMapping("/notfall/{kindId}")
    public StatusKindDTO bearbeitung(@PathVariable long kindId) {
        if (userRepository.existsById(kindId)){
            Kind kind= kindRepository.findById(kindId).get();
            kind.setTeilnahmeNotbetreuung(true);
            kindRepository.save(kind);
            return null;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kind nicht gefunden");
    }
}
