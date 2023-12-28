package com.example.kitanotbetreuungbackend.kind;

import java.util.List;

public class KitaGruppeDTO {
    private List<Kind> kinder;

    private boolean teilnahmeNotbetreuung;
    private long userId;



    private boolean notbetreuungNichtNotwendig;

    public KitaGruppeDTO(List<Kind> kinder, boolean teilnahmeNotbetreuung, long userId, boolean notbetreuungNichtNotwendig) {
        this.kinder = kinder;
        this.teilnahmeNotbetreuung = teilnahmeNotbetreuung;
        this.userId = userId;
        this.notbetreuungNichtNotwendig = notbetreuungNichtNotwendig;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<Kind> getKinder() {
        return kinder;
    }

    public void setKinder(List<Kind> kinder) {
        this.kinder = kinder;
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
