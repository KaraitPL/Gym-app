package com.example.gymapp.gym.repository.persistence;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.gym.repository.api.GymRepository;
import com.example.gymapp.trainer.entity.TrainerRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

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

    @Override
    public List<Gym> findAll() {
        // Utwórz CriteriaBuilder
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // Utwórz CriteriaQuery dla typu Gym
        CriteriaQuery<Gym> cq = cb.createQuery(Gym.class);

        // Określ główną encję (tabela "Gym")
        Root<Gym> gym = cq.from(Gym.class);

        // Dodaj klauzulę SELECT (cała encja)
        cq.select(gym);

        // Wykonaj zapytanie
        return em.createQuery(cq).getResultList();
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
