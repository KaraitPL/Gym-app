package com.example.gymapp.trainer.repository.persistence;

import com.example.gymapp.trainer.entity.Trainer;
import com.example.gymapp.trainer.repository.api.TrainerRepository;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class TrainerPersistenceRepository implements TrainerRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Trainer> findByName(String name) {
        try {
            // Utwórz CriteriaBuilder
            CriteriaBuilder cb = em.getCriteriaBuilder();

            // Utwórz CriteriaQuery dla typu Trainer
            CriteriaQuery<Trainer> cq = cb.createQuery(Trainer.class);

            // Określ główną encję (tabela "Trainer")
            Root<Trainer> trainer = cq.from(Trainer.class);

            // Dodaj warunek WHERE
            cq.select(trainer).where(cb.equal(trainer.get("name"), name));

            // Wykonaj zapytanie i zwróć wynik w Optional
            return Optional.of(em.createQuery(cq).getSingleResult());
        } catch (NoResultException ex) {
            // Obsługa przypadku braku wyników
            return Optional.empty();
        }
    }


    @Override
    public Optional<Trainer> find(UUID id) {
        return Optional.ofNullable(em.find(Trainer.class, id));
    }

    @Override
    public List<Trainer> findAll() {
        // Utwórz CriteriaBuilder
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // Utwórz CriteriaQuery dla typu Trainer
        CriteriaQuery<Trainer> cq = cb.createQuery(Trainer.class);

        // Określ główną encję (tabela "Trainer")
        Root<Trainer> trainer = cq.from(Trainer.class);

        // Dodaj klauzulę SELECT (cała encja Trainer)
        cq.select(trainer);

        // Wykonaj zapytanie
        return em.createQuery(cq).getResultList();
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
