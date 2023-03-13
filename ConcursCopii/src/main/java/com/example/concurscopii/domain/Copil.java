package com.example.concurscopii.domain;

public class Copil extends Entity<Long> {

    private String nume;

    private Integer varsta;

    public Copil(String nume, Integer varsta) {
        this.nume = nume;
        this.varsta = varsta;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getVarsta() {
        return varsta;
    }

    public void setVarsta(Integer varsta) {
        this.varsta = varsta;
    }

}
