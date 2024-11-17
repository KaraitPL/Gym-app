package com.example.gymapp.trainer.repository.persistence;

import com.example.gymapp.trainer.entity.Trainer;
import com.example.gymapp.trainer.repository.api.TrainerRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class TrainerPersistenceRepository implements TrainerRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Trainer> findByName(String name) {
        try {
            return Optional.of(em.createQuery("select t from Trainer t where t.name = :name", Trainer.class)
                    .setParameter("name", name)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }


    @Override
    public Optional<Trainer> find(UUID id) {
        return Optional.ofNullable(em.find(Trainer.class, id));
    }

    @Override
    public List<Trainer> findAll() {
        return em.createQuery("select t from Trainer t", Trainer.class).getResultList();
    }

    @Override
    public void create(Trainer entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Trainer entity) {
        em.remove(em.find(Trainer.class, entity.getId()));
    }

    @Override
    public void update(Trainer entity) {
        em.merge(entity);
    }
}
