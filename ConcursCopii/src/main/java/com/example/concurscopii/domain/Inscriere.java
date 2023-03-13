package com.example.concurscopii.domain;

public class Inscriere extends Entity<Long>{
    private Long idCopil;
    private Long idProba;

    public Inscriere(Long idCopil, Long idProba) {
        this.idCopil = idCopil;
        this.idProba = idProba;
    }

    public Long getIdCopil() {
        return idCopil;
    }

    public void setIdCopil(Long idCopil) {
        this.idCopil = idCopil;
    }

    public Long getIdProba() {
        return idProba;
    }

    public void setIdProba(Long idProba) {
        this.idProba = idProba;
    }
}
