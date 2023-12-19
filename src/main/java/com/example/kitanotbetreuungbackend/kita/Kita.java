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
    private List<KitaGruppe> kitaGruppe;
    private boolean isNotbetreuung;


    public Kita(String name, List<KitaGruppe> kitaGruppe) {
        this.name = name;
        this.kitaGruppe = kitaGruppe;
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

    public List<KitaGruppe> getKitaGruppe() {
        return kitaGruppe;
    }

    public void setKitaGruppe(List<KitaGruppe> kitaGruppe) {
        this.kitaGruppe = kitaGruppe;
    }

    public boolean isNotbetreuung() {
        return isNotbetreuung;
    }

    public void setNotbetreuung(boolean notbetreuung) {
        isNotbetreuung = notbetreuung;
    }
}

