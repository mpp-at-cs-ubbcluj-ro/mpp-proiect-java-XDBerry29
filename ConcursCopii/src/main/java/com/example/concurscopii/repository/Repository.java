package com.example.concurscopii.repository;

import com.example.concurscopii.domain.Entity;

import java.util.List;

public interface Repository<ID, E extends Entity<ID>> {

    E findOne(ID id);

    Iterable<E> findAll();

    void add(E entity);

    E delete(ID id);

    public void update(ID id, E car);
    //E update(E entity) throws ValidationException;

    List<E> getAllAsList();

}