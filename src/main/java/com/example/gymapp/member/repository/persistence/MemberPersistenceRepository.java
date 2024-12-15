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
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

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
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> member = cq.from(Member.class);

        cq.select(member)
                .where(cb.equal(member.get("trainer"), trainer));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Member> findAllByGym(Gym gym) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> member = cq.from(Member.class);

        cq.select(member)
                .where(cb.equal(member.get("gym"), gym));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public Optional<Member> findByIdAndTrainer(UUID id, Trainer trainer) {
        try {
            // Utwórz CriteriaBuilder
            CriteriaBuilder cb = em.getCriteriaBuilder();

            // Utwórz CriteriaQuery dla typu Member
            CriteriaQuery<Member> cq = cb.createQuery(Member.class);

            // Określ główną encję (tabela "Member")
            Root<Member> member = cq.from(Member.class);

            // Dodaj warunki WHERE
            cq.select(member).where(
                    cb.and(
                            cb.equal(member.get("id"), id),
                            cb.equal(member.get("trainer"), trainer)
                    )
            );

            // Wykonaj zapytanie i zwróć wynik w Optional
            return Optional.of(em.createQuery(cq).getSingleResult());
        } catch (NoResultException ex) {
            // Obsługa przypadku braku wyników
            return Optional.empty();
        }
    }
    @Override
    public Optional<Member> find(UUID id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    @Override
    public List<Member> findAll() {
        // Utwórz CriteriaBuilder
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // Utwórz CriteriaQuery dla typu Member
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);

        // Określ główną encję (tabela "Member")
        Root<Member> member = cq.from(Member.class);

        // Dodaj klauzulę SELECT (cała encja Member)
        cq.select(member);

        // Wykonaj zapytanie
        return em.createQuery(cq).getResultList();
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
