package com.example.kitanotbetreuungbackend.kitaGruppe;

import com.example.kitanotbetreuungbackend.kind.Kind;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class KitaGruppe {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "kitaGruppe", cascade = CascadeType.ALL)
    private List<Kind> kinder;

    public KitaGruppe() {
    }

    public KitaGruppe(String name, List<Kind> kinder) {
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

    public List<Kind> getKinder() {
        return kinder;
    }

    public void setKinder(List<Kind> kind) {
        this.kinder = kind;
    }
}
