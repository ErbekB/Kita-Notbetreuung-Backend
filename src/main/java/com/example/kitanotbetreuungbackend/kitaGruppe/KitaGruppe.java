package com.example.kitanotbetreuungbackend.kitaGruppe;

import com.example.kitanotbetreuungbackend.kind.Kind;
import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
public class KitaGruppe {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany
    private ArrayList<Kind> kinder;

    public KitaGruppe() {
    }

    public KitaGruppe(String name, ArrayList<Kind> kinder) {
        this.name = name;
        this.kinder = kinder;
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

    public ArrayList<Kind> getKinder() {
        return kinder;
    }

    public void setKinder(ArrayList<Kind> kind) {
        this.kinder = kind;
    }
}
