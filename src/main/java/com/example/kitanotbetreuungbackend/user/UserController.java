package com.example.kitanotbetreuungbackend.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/index/{id}")
    public IndexDTO hauptseite (@RequestParam long id) {
        return new IndexDTO();
    }

    @PostMapping("/index/{id}")
    public StatusDTO statusNotbetreuung(@RequestParam long id){
        return new StatusDTO();
    }



}
