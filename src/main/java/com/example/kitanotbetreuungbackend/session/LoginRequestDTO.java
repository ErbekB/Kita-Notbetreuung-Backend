package com.example.kitanotbetreuungbackend.session;

public class LoginRequestDTO {

    private String name;
    private String passwort;

    public LoginRequestDTO(String name, String passwort) {
        this.name = name;
        this.passwort = passwort;
    }
    public String getName() {
        return name;
    }

    public String getPasswort() {
        return passwort;
    }
}
