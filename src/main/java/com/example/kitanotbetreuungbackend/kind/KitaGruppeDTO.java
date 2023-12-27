package com.example.kitanotbetreuungbackend.kind;

import java.util.List;

public class KitaGruppeDTO {
    private List<Kind> kinder;

    private boolean teilnahmeNotbetreuung;
    private long userId;

    public KitaGruppeDTO(List<Kind> kinder, boolean teilnahmeNotbetreuung, long userId) {
        this.kinder = kinder;
        this.teilnahmeNotbetreuung = teilnahmeNotbetreuung;
        this.userId = userId;
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
}
