package com.example.kitanotbetreuungbackend.kind;

import java.util.List;

public class KitaGruppeDTO {
    private List<Kind> kinder;

    private boolean teilnahmeNotbetreuung;

    public KitaGruppeDTO(List<Kind> kinder, boolean teilnahmeNotbetreuung) {
        this.kinder = kinder;
        this.teilnahmeNotbetreuung = teilnahmeNotbetreuung;
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
