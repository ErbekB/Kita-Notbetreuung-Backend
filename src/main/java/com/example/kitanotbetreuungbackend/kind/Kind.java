package com.example.kitanotbetreuungbackend.kind;

import com.example.kitanotbetreuungbackend.kitaGruppe.KitaGruppe;
import com.example.kitanotbetreuungbackend.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Kind {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vorname;
    private String nachname;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "kitagruppe_id")
    private KitaGruppe kitaGruppe;
    private int counter = 0;
    private boolean teilnahmeNotbetreuung = false;
    private boolean notbetreuungNichtNotwendig = false;

    public Kind() {
    }

    public Kind(String vorname, String nachname, User user, KitaGruppe kitaGruppe) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.user = user;
        this.kitaGruppe = kitaGruppe;
        this.counter = 0;
        this.teilnahmeNotbetreuung = false;
        this.notbetreuungNichtNotwendig = false;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public KitaGruppe getKitaGruppe() {
        return kitaGruppe;
    }

    public void setKitaGruppe(KitaGruppe kitaGruppe) {
        this.kitaGruppe = kitaGruppe;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public boolean isTeilnahmeNotbetreuung() {
        return teilnahmeNotbetreuung;
    }

    public void setTeilnahmeNotbetreuung(boolean teilnahmeNotbetreuung) {
        this.teilnahmeNotbetreuung = teilnahmeNotbetreuung;
    }

    public boolean isNotbetreuungNichtNotwendig() {
        return notbetreuungNichtNotwendig;
    }

    public void setNotbetreuungNichtNotwendig(boolean notbetreuungNichtNotwendig) {
        this.notbetreuungNichtNotwendig = notbetreuungNichtNotwendig;
    }
}
