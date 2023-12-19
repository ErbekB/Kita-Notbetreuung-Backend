package com.example.kitanotbetreuungbackend.user;

import com.example.kitanotbetreuungbackend.kind.Kind;
import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean isAdmin;
    @OneToMany
    private ArrayList<Kind> kind;
    private String passwort;

    public User() {
    }

    public User(ArrayList<Kind> kind, String name, String passwort) {
        this.name = name;
        this.isAdmin= false;
        this.kind = kind;
        this.passwort = passwort;
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

    public ArrayList<Kind> getKind() {
        return kind;
    }

    public void setKind(ArrayList<Kind> kind) {
        this.kind = kind;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }
}
