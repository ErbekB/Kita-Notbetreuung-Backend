package com.example.kitanotbetreuungbackend.user;

import com.example.kitanotbetreuungbackend.kind.Kind;

import java.util.ArrayList;

public class IndexDTO {
    private boolean isAdmin;
    private boolean isNotbetreuung;
    private ArrayList<Kind> kinder;

    public IndexDTO(boolean isAdmin, boolean isNotbetreuung, ArrayList<Kind> kinder) {
        this.isAdmin = isAdmin;
        this.isNotbetreuung = isNotbetreuung;
        this.kinder = kinder;
    }


    public boolean getAdmin() {
        return isAdmin;
    }

    public boolean isNotbetreuung() {
        return isNotbetreuung;
    }

    public ArrayList<Kind> getKindList() {
        return kinder;
    }

}
