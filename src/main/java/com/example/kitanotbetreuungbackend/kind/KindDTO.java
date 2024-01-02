package com.example.kitanotbetreuungbackend.kind;

public class KindDTO {
    private Long id;
    private String vorname;
    private String nachname;
    private int counter;
    private Long elternId;
    private boolean teilnahmeNotbetreuung;
    private boolean notbetreuungNichtNotwendig;

    public KindDTO(Long id, String vorname, String nachname, int counter, Long elternId, boolean teilnahmeNotbetreuung, boolean notbetreuungNichtNotwendig) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.counter = counter;
        this.elternId = elternId;
        this.teilnahmeNotbetreuung = teilnahmeNotbetreuung;
        this.notbetreuungNichtNotwendig = notbetreuungNichtNotwendig;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Long getElternId() {
        return elternId;
    }

    public void setElternId(Long elternId) {
        this.elternId = elternId;
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
