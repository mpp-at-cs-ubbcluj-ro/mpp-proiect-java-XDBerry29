package com.example.concurscopii.domain;

public class Organizator extends Entity<String>{

    private String parola;

    public Organizator(String username, String parola) {
        setID(username);
        this.parola = parola;
    }

    public String getUsername() {
        return getID();
    }

    public void setUsername(String username) {
        setID(username);
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }
}
