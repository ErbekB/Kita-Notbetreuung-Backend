package com.example.kitanotbetreuungbackend.user;

public class KindHinzufuegenDTO {
    private String vorname;
    private String nachname;

    public KindHinzufuegenDTO() {}

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }
}