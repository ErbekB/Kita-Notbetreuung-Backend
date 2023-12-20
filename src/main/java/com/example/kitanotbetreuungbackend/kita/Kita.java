package com.example.kitanotbetreuungbackend.kita;

import com.example.kitanotbetreuungbackend.kitaGruppe.KitaGruppe;
import jakarta.persistence.*;

import java.util.List;


@Entity
public class Kita {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany
    private List<KitaGruppe> kitaGruppen;
    private boolean isNotbetreuung;


    public Kita(String name, List<KitaGruppe> kitaGruppen) {
        this.name = name;
        this.kitaGruppen = kitaGruppen;
        this.isNotbetreuung = false;
    }

    public Kita() {

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

