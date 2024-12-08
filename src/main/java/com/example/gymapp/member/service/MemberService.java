package com.example.gymapp.member.service;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.gym.repository.api.GymRepository;
import com.example.gymapp.gym.service.GymService;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.member.repository.api.MemberRepository;
import com.example.gymapp.trainer.entity.Trainer;
import com.example.gymapp.trainer.entity.TrainerRoles;
import com.example.gymapp.trainer.repository.api.TrainerRepository;
import com.example.gymapp.trainer.service.TrainerService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class MemberService {

    private final MemberRepository memberRepository;

    private final TrainerService trainerService;

    private final GymService gymService;

    private final SecurityContext securityContext;

    @Inject
    public MemberService(MemberRepository memberRepository, TrainerService trainerService, GymService gymService, @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext) {
        this.memberRepository = memberRepository;
        this.trainerService = trainerService;
        this.gymService = gymService;
        this.securityContext = securityContext;
    }

    @RolesAllowed(TrainerRoles.USER)
    public Optional<Member> find(UUID id) { return memberRepository.find(id); }

    @RolesAllowed(TrainerRoles.USER)
    public Optional<Member> find(Trainer trainer, UUID id) {
        return memberRepository.findByIdAndTrainer(id, trainer);
    }

    @RolesAllowed(TrainerRoles.USER)
    public List<Member> findAll() { return memberRepository.findAll(); }

    @RolesAllowed(TrainerRoles.USER)
    public Optional<Member> findForCallerPrincipal(UUID gymId, UUID memberId) {
        checkAdminRoleOrOwner(memberRepository.find(memberId));
        return findByGymAndMember(gymId, memberId);
    }

    public Optional<Member> findByGymAndMember(UUID gymId, UUID memberId){
        Gym gym = gymService.find(gymId)
                .orElseThrow(() -> new NotFoundException("Gym not found: " + gymId));

        return memberRepository.find(memberId)
                .filter(member -> member.getGym().getId().equals(gym.getId()));
    }

    @RolesAllowed(TrainerRoles.USER)
    public void create(Member member, UUID trainerId, UUID gymId) {

        Trainer trainer = trainerService.find(trainerId).orElseThrow(() -> new NotFoundException("Trainer not found: " + trainerId));

        Gym gym = gymService.find(gymId).orElseThrow(() -> new NotFoundException("Gym not found: " + gymId));

        memberRepository.create(member);

        List<Member> trainerMembers = new ArrayList<>(trainer.getMembers());
        trainerMembers.add(member);
        trainer.setMembers(trainerMembers);
        trainerService.update(trainer);

        List<Member> gymMembers = new ArrayList<>(gym.getMembers());
        gymMembers.add(member);
        gym.setMembers(gymMembers);
        gymService.update(gym);
    }

    @RolesAllowed(TrainerRoles.USER)
    public void update(Member member, UUID initialGym) {
        checkAdminRoleOrOwner(memberRepository.find(member.getId()));

        Trainer trainer = trainerService.find(member.getTrainer().getId())
                .orElseThrow(() -> new NotFoundException("Trainer not found: " + member.getTrainer().getId()));

        Gym newGym = gymService.find(member.getGym().getId())
                .orElseThrow(() -> new NotFoundException("Gym not found: " + member.getGym().getId()));

        if (!initialGym.equals(newGym.getId())) {
            Gym oldGym = gymService.find(initialGym)
                    .orElseThrow(() -> new NotFoundException("Initial gym not found: " + initialGym));

            oldGym.getMembers().removeIf(oldGymMember -> oldGymMember.getId().equals(member.getId()));
            gymService.update(oldGym);
        }

        boolean trainerMemberUpdated = trainer.getMembers().removeIf(trainerMember -> trainerMember.getId().equals(member.getId()));
        if (trainerMemberUpdated) {
            trainer.getMembers().add(member);
        } else {
            throw new NotFoundException("Member not found in trainer's members: " + member.getId());
        }

        newGym.getMembers().removeIf(gymMember -> gymMember.getId().equals(member.getId()));
        newGym.getMembers().add(member);

        trainerService.update(trainer);
        gymService.update(newGym);
        memberRepository.update(member);
    }

    @RolesAllowed(TrainerRoles.USER)
    public void delete(UUID id) {
        checkAdminRoleOrOwner(memberRepository.find(id));

        Member member = memberRepository.find(id)
                .orElseThrow(NotFoundException::new);

        Trainer trainer = trainerService.find(member.getTrainer().getId())
                .orElseThrow(() -> new NotFoundException("Trainer not found: " + member.getTrainer().getId()));

        Gym gym = gymService.find(member.getGym().getId())
                .orElseThrow(() -> new NotFoundException("Gym not found: " + member.getGym().getId()));

        trainer.getMembers().removeIf(u -> u.getId().equals(member.getId()));
        gym.getMembers().removeIf(f -> f.getId().equals(member.getId()));

        trainerService.update(trainer);
        gymService.update(gym);
        memberRepository.delete(member);
    }

    private void checkAdminRoleOrOwner(Optional<Member> member) {
        if (securityContext.isCallerInRole(TrainerRoles.ADMIN)) {
            return;
        }
        if (securityContext.isCallerInRole(TrainerRoles.USER)
                && member.isPresent()
                && member.get().getTrainer().getName().equals(securityContext.getCallerPrincipal().getName())) {
            return;
        }
        throw new NotAuthorizedException("Caller not authorized.");
    }

    public Optional<List<Member>> findAllByTrainer(UUID id) {
        return trainerService.find(id)
                .map(memberRepository::findAllByTrainer);
    }

    public Optional<List<Member>> findAllByGym(UUID id){
        return gymService.find(id)
                .map(memberRepository::findAllByGym);
    }



}

