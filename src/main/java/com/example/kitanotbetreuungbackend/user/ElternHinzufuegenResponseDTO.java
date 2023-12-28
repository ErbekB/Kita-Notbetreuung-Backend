package com.example.kitanotbetreuungbackend.user;

import com.example.kitanotbetreuungbackend.kind.Kind;

import java.util.List;
import java.util.stream.Collectors;

public class ElternHinzufuegenResponseDTO {
    private Long id;
    private String name;
    private KindInfo neuesKind;
    private List<KindInfo> andereKinder;

    public ElternHinzufuegenResponseDTO(User user, Kind neuesKind) {
        this.id = user.getId();
        this.name = user.getName();
        this.neuesKind = new KindInfo(neuesKind);
        this.andereKinder = user.getKind().stream()
                .filter(kind -> !kind.getId().equals(neuesKind.getId()))
                .map(KindInfo::new)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KindInfo getNeuesKind() {
        return neuesKind;
    }

    public void setNeuesKind(KindInfo neuesKind) {
        this.neuesKind = neuesKind;
    }

    public List<KindInfo> getAndereKinder() {
        return andereKinder;
    }

    public void setAndereKinder(List<KindInfo> andereKinder) {
        this.andereKinder = andereKinder;
    }

    public static class KindInfo {
        private Long id;
        private String vorname;
        private String nachname;

        public KindInfo(Kind kind) {
            this.id = kind.getId();
            this.vorname = kind.getVorname();
            this.nachname = kind.getNachname();
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
    }
}
