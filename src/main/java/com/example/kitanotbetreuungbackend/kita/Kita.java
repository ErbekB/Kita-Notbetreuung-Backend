package com.example.kitanotbetreuungbackend.kita;

import com.example.kitanotbetreuungbackend.kitaGruppe.KitaGruppe;
import com.example.kitanotbetreuungbackend.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Kita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int postleitzahl;
    private boolean isNotbetreuung;
    @OneToMany(mappedBy = "kita", cascade = CascadeType.ALL)
    private List<KitaGruppe> kitaGruppen = new ArrayList<>();


    public Kita() {
    }

    public Kita(String name, List<KitaGruppe> kitaGruppen) {
        this.name = name;
        this.kitaGruppen = kitaGruppen;
        this.isNotbetreuung = true;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPostleitzahl() {
        return postleitzahl;
    }

    public void setPostleitzahl(int postleitzahl) {
        this.postleitzahl = postleitzahl;
    }

    public List<KitaGruppe> getKitaGruppen() {
        return kitaGruppen;
    }

    public void setKitaGruppen(List<KitaGruppe> kitaGruppe) {
        this.kitaGruppen = kitaGruppe;
    }

    public boolean isNotbetreuung() {
        return isNotbetreuung;
    }

    public void setNotbetreuung(boolean notbetreuung) {
        isNotbetreuung = notbetreuung;
    }
}

