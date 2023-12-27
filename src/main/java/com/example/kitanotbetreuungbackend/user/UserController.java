package com.example.kitanotbetreuungbackend.user;

import com.example.kitanotbetreuungbackend.kind.Kind;
import com.example.kitanotbetreuungbackend.kita.Kita;
import com.example.kitanotbetreuungbackend.kita.KitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class UserController {

    private KitaRepository kitaRepository;
    private UserRepository userRepository;

    @Autowired
    public UserController(KitaRepository kitaRepository, UserRepository userRepository) {
        this.kitaRepository = kitaRepository;
        this.userRepository = userRepository;
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

    @PostMapping("/index/{id}")
    public Kita statusNotbetreuung(@PathVariable long id){

        if (userRepository.existsById(id)) {
            User Benutzer = userRepository.findById(id).get();
            if(Benutzer.isAdmin()) {
                Benutzer.getKita().setNotbetreuung(!Benutzer.getKita().isNotbetreuung());
                userRepository.save(Benutzer);
                return Benutzer.getKita();
            }
        }
        return null;
    }
}
