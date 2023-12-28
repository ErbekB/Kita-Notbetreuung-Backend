package com.example.kitanotbetreuungbackend.user;

public class ElternHinzufuegenDTO {
    private String name;
    private String kindVorname;
    private String kindNachname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKindVorname() {
        return kindVorname;
    }

    public void setKindVorname(String kindVorname) {
        this.kindVorname = kindVorname;
    }

    public String getKindNachname() {
        return kindNachname;
    }

    public void setKindNachname(String kindNachname) {
        this.kindNachname = kindNachname;
    }
}
