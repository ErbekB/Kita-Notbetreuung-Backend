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

    @GetMapping("/notfall/{id}")
    public KitaGruppeDTO uebersicht(@PathVariable long id) {
        if (userRepository.existsById(id)) {
            User benutzer = userRepository.findById(id).get();
            List<Kind> KinderListe = benutzer.getKind().get(0).getKitaGruppe().getKinder();
            boolean teilnahme = benutzer.getKind().get(0).isTeilnahmeNotbetreuung();
            return new KitaGruppeDTO(KinderListe, teilnahme);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Benutzer nicht gefunden");
    }

    @PostMapping("/notfall/{kindId}") //Todo return data to check the counter
    public StatusKindDTO bearbeitung(@PathVariable long kindId) {
        if (userRepository.existsById(kindId)) {
            Kind kind = kindRepository.findById(kindId).get();
            kind.setTeilnahmeNotbetreuung(!kind.isTeilnahmeNotbetreuung());
            kind.setCounter(kind.getCounter()+1); //countertest
            kindRepository.save(kind);
            return null;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kind nicht gefunden");
    }
    @PostMapping("/notfall/aendern/{kindId}")
    public StatusKindDTO aendern(@PathVariable long kindId) {
        if (userRepository.existsById(kindId)) {
            Kind kind = kindRepository.findById(kindId).get();
            kind.setTeilnahmeNotbetreuung(!kind.isTeilnahmeNotbetreuung());
            kind.setCounter(kind.getCounter()-1);
            kindRepository.save(kind);
            return null;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kind nicht gefunden");
    }
    //Teilnahme zurückziehen
    @PostMapping("notfall/teilnahme/{kindId}")
    public StatusKindDTO keineTeilnahme(@PathVariable long kindId) {
    if(userRepository.existsById(kindId)){
        Kind kind = kindRepository.findById(kindId).get();

        kindRepository.delete(kind);
    }
    return null;
    }
}