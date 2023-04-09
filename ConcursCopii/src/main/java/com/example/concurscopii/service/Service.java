package com.example.concurscopii.service;

import com.example.concurscopii.domain.Copil;
import com.example.concurscopii.domain.Inscriere;
import com.example.concurscopii.domain.Organizator;
import com.example.concurscopii.domain.Proba;
import com.example.concurscopii.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Service {

    Repository<String,Organizator> RepoOrganizator;
    Repository<Long, Copil> RepoCopil;
    Repository<Long, Inscriere> RepoInscriere;
    Repository<Long, Proba> RepoProba;

    public Service(Repository<String, Organizator> repoOrganizator, Repository<Long, Copil> repoCopil, Repository<Long, Inscriere> repoInscriere, Repository<Long, Proba> repoProba) {
        RepoOrganizator = repoOrganizator;
        RepoCopil = repoCopil;
        RepoInscriere = repoInscriere;
        RepoProba = repoProba;
    }

    public List<Copil> getAllCopii()
    {
        return RepoCopil.getAllAsList();
    }
    public List<Inscriere> getAllInscrieri()
    {
        return RepoInscriere.getAllAsList();
    }
    public List<Proba> getAllProbe()
    {
        return RepoProba.getAllAsList();
    }

    public boolean verify_account(String Username,String Parola){
        Organizator O= RepoOrganizator.findOne(Username);
        if (O != null)
            return Objects.equals(O.getParola(), Parola);

        return false;
    }

    public List<Integer> setAllVarste()
    {
        List<Integer> varste = new ArrayList<>();

        for(int i=1; i<=18; i++)
            varste.add(i);

        return varste;
    }

    public List<Copil> findAllCopiiInscrisi(Long idProba){
        List<Inscriere> all = getAllInscrieri();
        List<Inscriere> filtered = all.stream().filter(inscriere -> Objects.equals(inscriere.getIdProba(), idProba)).toList();

        List<Copil> filtered_copii = new ArrayList<>();

        for(Inscriere inscriere: filtered){
            System.out.println("ID copil:" + inscriere.getIdCopil());
            filtered_copii.add(RepoCopil.findOne(inscriere.getIdCopil()));
        }

        return filtered_copii;
    }

    public Copil findCopil(String nume, int varsta){
        List<Copil> all = getAllCopii();

        List<Copil> filtered = all.stream().filter(copil -> Objects.equals(copil.getNume(), nume) && copil.getVarsta() == varsta).toList();

        if(filtered.size() >= 1)
            return filtered.get(0);
        else return null;
    }

    public int findInregistrare(Long idCopil){
        List<Inscriere> all = getAllInscrieri();

        List<Inscriere> filtered = all.stream().filter(inscriere -> Objects.equals(inscriere.getIdCopil(), idCopil)).toList();

        return filtered.size();
    }

    public void addCopil(String nume, int varsta){
        RepoCopil.add(new Copil(nume, varsta));
    }

    public void addInregistrare(Long idCopil, Long idProba){
        RepoInscriere.add(new Inscriere(idCopil, idProba));
    }
}
