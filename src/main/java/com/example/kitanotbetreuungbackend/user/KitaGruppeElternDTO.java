package com.example.kitanotbetreuungbackend.user;

import java.util.List;

public class KitaGruppeElternDTO {
    private String kitaName;
    private String kitaGruppeName;
    private List<ElternKindDTO> elternMitKindern;

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

    public List<ElternKindDTO> getElternMitKindern() {
        return elternMitKindern;
    }

    public void setElternMitKindern(List<ElternKindDTO> elternMitKindern) {
        this.elternMitKindern = elternMitKindern;
    }

    public static class ElternKindDTO {
        private Long elternId;
        private String elternName;
        private List<KindDTO> kinder;

        public Long getElternId() {
            return elternId;
        }

        public void setElternId(Long elternId) {
            this.elternId = elternId;
        }

        public String getElternName() {
            return elternName;
        }

        public void setElternName(String elternName) {
            this.elternName = elternName;
        }

        public List<KindDTO> getKinder() {
            return kinder;
        }

        public void setKinder(List<KindDTO> kinder) {
            this.kinder = kinder;
        }

        public static class KindDTO {
            private Long kindId;
            private String vorname;
            private String nachname;

            public KindDTO(Long kindId, String vorname, String nachname) {
                this.kindId = kindId;
                this.vorname = vorname;
                this.nachname = nachname;
            }

            public Long getKindId() {
                return kindId;
            }

            public void setKindId(Long kindId) {
                this.kindId = kindId;
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
}