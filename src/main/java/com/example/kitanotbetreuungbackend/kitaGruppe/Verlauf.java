package com.example.kitanotbetreuungbackend.kitaGruppe;

import com.example.kitanotbetreuungbackend.kind.Kind;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Verlauf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate datum;
    @OneToMany
    private List<Kind> kinder;

    public Verlauf() {
    }

    public Verlauf(List<Kind> kinder) {
        this.datum = LocalDate.now();
        this.kinder = kinder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public List<Kind> getKinder() {
        return kinder;
    }

    public void setKinder(List<Kind> kinder) {
        this.kinder = kinder;
    }
}
