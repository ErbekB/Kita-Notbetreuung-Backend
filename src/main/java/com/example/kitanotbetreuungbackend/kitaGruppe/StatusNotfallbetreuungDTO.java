package com.example.kitanotbetreuungbackend.kitaGruppe;

public class StatusNotfallbetreuungDTO {
    private boolean abstimmungAbgeschlossen;

    public StatusNotfallbetreuungDTO() {
    }

    public StatusNotfallbetreuungDTO(boolean abstimmungAbgeschlossen) {
        this.abstimmungAbgeschlossen = abstimmungAbgeschlossen;
    }

    public boolean isAbstimmungAbgeschlossen() {
        return abstimmungAbgeschlossen;
    }

    public void setAbstimmungAbgeschlossen(boolean abstimmungAbgeschlossen) {
        this.abstimmungAbgeschlossen = abstimmungAbgeschlossen;
    }
}
