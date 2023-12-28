package com.example.kitanotbetreuungbackend.session;

public class RegistrierenRequestDTO {
    private String name;
    private String passwort;
    private String kita;
    private String postleitzahl;
    private String kitaGruppe;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getKita() {
        return kita;
    }

    public void setKita(String kita) {
        this.kita = kita;
    }

    public String getPostleitzahl() {
        return postleitzahl;
    }

    public void setPostleitzahl(String postleitzahl) {
        this.postleitzahl = postleitzahl;
    }

    public String getKitaGruppe() {
        return kitaGruppe;
    }

    public void setKitaGruppe(String kitaGruppe) {
        this.kitaGruppe = kitaGruppe;
    }
}
