package com.example.kitanotbetreuungbackend.kind;

import java.util.List;

public class KitaGruppeDTO {
    private List<KindDTO> kinder;
    private boolean teilnahmeNotbetreuung;
    private long userId;
    private boolean notbetreuungNichtNotwendig;

    public KitaGruppeDTO(List<KindDTO> kinder, boolean teilnahmeNotbetreuung, long userId, boolean notbetreuungNichtNotwendig) {
        this.kinder = kinder;
        this.teilnahmeNotbetreuung = teilnahmeNotbetreuung;
        this.userId = userId;
        this.notbetreuungNichtNotwendig = notbetreuungNichtNotwendig;
    }

    // Getter und Setter
    public List<KindDTO> getKinder() {
        return kinder;
    }

    public void setKinder(List<KindDTO> kinder) {
        this.kinder = kinder;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
