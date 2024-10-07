package com.example.gymapp.repository.api;
import java.util.Optional;
import java.util.List;
public interface Repository<E, K> {

    Optional<E> find(K id);

    List<E> findAll();

    void create(E entity);

    void delete(E entity);

    void update(E entity);

}

