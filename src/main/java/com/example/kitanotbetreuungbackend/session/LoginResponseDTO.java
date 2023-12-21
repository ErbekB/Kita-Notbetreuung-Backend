package com.example.kitanotbetreuungbackend.session;

public class LoginResponseDTO {

    private String name;

    public LoginResponseDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

