package com.example.gymapp.member.repository.persistence;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.member.repository.api.MemberRepository;
import com.example.gymapp.trainer.entity.Trainer;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class MemberPersistenceRepository implements MemberRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Member> findAllByTrainer(Trainer trainer) {
        return em.createQuery("select m from Member m where m.trainer = :trainer", Member.class)
                .setParameter("trainer", trainer)
                .getResultList();

    }

    @Override
    public List<Member> findAllByGym(Gym gym) {
        return em.createQuery("select m from Member m where m.gym = :gym", Member.class)
                .setParameter("gym", gym)
                .getResultList();
    }

    @Override
    public Optional<Member> findByIdAndTrainer(UUID id, Trainer trainer) {
        try {
            return Optional.of(em.createQuery("select c from Member c where c.id = :id and c.trainer = :trainer", Member.class)
                    .setParameter("trainer", trainer)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
    @Override
    public Optional<Member> find(UUID id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    @Override
    public void create(Member entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Member entity) {
        em.remove(em.find(Member.class, entity.getId()));
    }

    @Override
    public void update(Member entity) {
        em.merge(entity);
    }
}
