package com.example.kitanotbetreuungbackend.kind;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class KindController {
    @GetMapping("/notfall/{id}")
    public KitaGruppeDTO uebersicht(@RequestParam long id){
      return new KitaGruppeDTO();
    }
    @PostMapping("/notfall/{id}")
    public StatusKindDTO bearbeitung(@RequestParam long id){
        return new StatusKindDTO();
    }
}
