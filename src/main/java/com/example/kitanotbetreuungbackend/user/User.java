package com.example.kitanotbetreuungbackend.user;

import com.example.kitanotbetreuungbackend.kind.Kind;
import com.example.kitanotbetreuungbackend.kita.Kita;
import com.example.kitanotbetreuungbackend.session.Session;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String passwort;
    private boolean isAdmin;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Kind> kind = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Session> sessions;
    @ManyToOne
    private Kita kita;

    public User() {
    }

    public User(List<Kind> kind, String name, Kita kita, String passwort) {
        this.kind = kind;
        this.name = name;
        this.kita = kita;
        this.passwort = passwort;
        this.isAdmin = false;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<Kind> getKind() {
        return kind;
    }

    public void setKind(List<Kind> kind) {
        this.kind = kind;
    }

    public void addKind(Kind kind) {
        this.kind.add(kind);
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public Kita getKita() {
        return kita;
    }

    public void setKita(Kita kita) {
        this.kita = kita;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}
