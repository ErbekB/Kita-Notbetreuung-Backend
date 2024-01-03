package com.example.kitanotbetreuungbackend.kitaGruppe;

import java.util.List;

public class VerlaufDTO {
    private List<Verlauf> verläufe;

    public VerlaufDTO(List<Verlauf> verläufe) {
        this.verläufe = verläufe;
    }

    public List<Verlauf> getVerläufe() {
        return verläufe;
    }

    public void setVerläufe(List<Verlauf> verläufe) {
        this.verläufe = verläufe;
    }
}
