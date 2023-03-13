package com.example.concurscopii.repository;

import com.example.concurscopii.domain.Entity;

import java.util.List;

public interface Repository<ID, E extends Entity<ID>> {

    E findOne(ID id);

    //Iterable<E> findAll();

    E save(E entity); //throws ValidationException;

    E delete(ID id);

    E update(E entity); //throws ValidationException;

    List<E> getAllAsList();

}