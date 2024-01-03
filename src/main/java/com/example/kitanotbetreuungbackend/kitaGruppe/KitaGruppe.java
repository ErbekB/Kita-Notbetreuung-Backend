package com.example.kitanotbetreuungbackend.kitaGruppe;

import com.example.kitanotbetreuungbackend.kind.Kind;
import com.example.kitanotbetreuungbackend.kita.Kita;
import com.example.kitanotbetreuungbackend.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class KitaGruppe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "kita_id")
    private Kita kita;
    @OneToMany(mappedBy = "kitaGruppe", cascade = CascadeType.ALL)
    private List<Kind> kinder = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "admin_id")
    private User admin;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "verlauf_id")
    private List<Verlauf> verläufe = new ArrayList<>();
    private boolean abstimmungAbgeschlossen = false;


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

    public Kita getKita() {
        return kita;
    }

    public void setKita(Kita kita) {
        this.kita = kita;
    }

    public List<Kind> getKinder() {
        return kinder;
    }

    public void setKinder(List<Kind> kind) {
        this.kinder = kind;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public boolean isAbstimmungAbgeschlossen() {
        return abstimmungAbgeschlossen;
    }

    public void setAbstimmungAbgeschlossen(boolean abstimmungAbgeschlossen) {
        this.abstimmungAbgeschlossen = abstimmungAbgeschlossen;
    }

    public List<Verlauf> getVerläufe() {
        return verläufe;
    }

    public void setVerläufe(List<Verlauf> verläufe) {
        this.verläufe = verläufe;
    }

    public void addVerlauf(Verlauf verlauf) {
        this.verläufe.add(verlauf);
    }
}
