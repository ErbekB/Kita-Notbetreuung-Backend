package com.example.kitanotbetreuungbackend.user;

import com.example.kitanotbetreuungbackend.kind.Kind;

import java.util.ArrayList;
import java.util.List;

public class IndexDTO {
    private boolean isAdmin;
    private boolean isNotbetreuung;
    private List<Kind> kinder;

    public IndexDTO(boolean isAdmin, boolean isNotbetreuung, List<Kind> kinder) {
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

    public List<Kind> getKindList() {
        return kinder;
    }

}
