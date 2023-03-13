package com.example.concurscopii.domain;

public class Proba extends Entity<Long>{

    private String nume;

    private int varsta_min;

    private int varsta_max;


    public Proba(String nume, int varsta_min, int varsta_max) {
        this.nume = nume;
        this.varsta_min = varsta_min;
        this.varsta_max = varsta_max;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getVarsta_min() {
        return varsta_min;
    }

    public void setVarsta_min(int varsta_min) {
        this.varsta_min = varsta_min;
    }

    public int getVarsta_max() {
        return varsta_max;
    }

    public void setVarsta_max(int varsta_max) {
        this.varsta_max = varsta_max;
    }
}
