package com.example.kitanotbetreuungbackend.user;

import com.example.kitanotbetreuungbackend.kind.Kind;

import java.util.ArrayList;
import java.util.List;

public class IndexDTO {
    private boolean isAdmin;
    private boolean isNotbetreuung;
    private List<Kind> kinder;
    private String kitaName;
    private String kitaGruppeName;

    public IndexDTO(boolean isAdmin, boolean isNotbetreuung, List<Kind> kinder, String kitaName, String kitaGruppeName) {
        this.isAdmin = isAdmin;
        this.isNotbetreuung = isNotbetreuung;
        this.kinder = kinder;
        this.kitaName = kitaName;
        this.kitaGruppeName = kitaGruppeName;
    }


    public boolean getAdmin() {
        return isAdmin;
    }

    public boolean isNotbetreuung() {
        return isNotbetreuung;
    }

    public List<Kind> getKindList() {
        return kinder;
    }

    public String getKitaName() {
        return kitaName;
    }

    public void setKitaName(String kitaName) {
        this.kitaName = kitaName;
    }

    public String getKitaGruppeName() {
        return kitaGruppeName;
    }

    public void setKitaGruppeName(String kitaGruppeName) {
        this.kitaGruppeName = kitaGruppeName;
    }
}
