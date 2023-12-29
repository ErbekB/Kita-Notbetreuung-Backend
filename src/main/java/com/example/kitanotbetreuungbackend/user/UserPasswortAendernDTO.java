package com.example.kitanotbetreuungbackend.user;

public class UserPasswortAendernDTO {
    private String passwort;

    public UserPasswortAendernDTO(String passwort) {
        this.passwort = passwort;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }
}
