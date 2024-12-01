package com.example.gymapp.gym.repository.persistence;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.gym.repository.api.GymRepository;
import com.example.gymapp.trainer.entity.TrainerRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class GymPersistenceRepository implements GymRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Gym> find(UUID id) {
        return Optional.ofNullable(em.find(Gym.class, id));
    }

    @RolesAllowed(TrainerRoles.USER)
    @Override
    public List<Gym> findAll() {
        return em.createQuery("select g from Gym g", Gym.class).getResultList();
    }

    @Override
    public void create(Gym entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Gym entity) {
        em.remove(em.find(Gym.class, entity.getId()));
    }

    @Override
    public void update(Gym entity) {
        em.merge(entity);
    }
}
