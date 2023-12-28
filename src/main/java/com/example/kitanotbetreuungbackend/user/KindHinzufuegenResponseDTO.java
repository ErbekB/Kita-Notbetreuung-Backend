package com.example.kitanotbetreuungbackend.user;

import com.example.kitanotbetreuungbackend.kind.Kind;

public class KindHinzufuegenResponseDTO {
    private Long id;
    private String vorname;
    private String nachname;

    KindHinzufuegenResponseDTO(Kind kind) {
        this.id = kind.getId();
        this.vorname = kind.getVorname();
        this.nachname = kind.getNachname();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
