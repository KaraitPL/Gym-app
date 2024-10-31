package com.example.gymapp.member.service;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.gym.repository.api.GymRepository;
import com.example.gymapp.gym.service.GymService;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.member.repository.api.MemberRepository;
import com.example.gymapp.trainer.entity.Trainer;
import com.example.gymapp.trainer.repository.api.TrainerRepository;
import com.example.gymapp.trainer.service.TrainerService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class MemberService {

    private final MemberRepository memberRepository;

    private final GymRepository gymRepository;

    private final TrainerService trainerService;

    private final TrainerRepository trainerRepository;
    private final GymService gymService;

    @Inject
    public MemberService(MemberRepository memberRepository, TrainerService trainerService, GymRepository gymRepository, TrainerRepository trainerRepository, GymService gymService) {
        this.memberRepository = memberRepository;
        this.trainerService = trainerService;
        this.gymRepository = gymRepository;
        this.trainerRepository = trainerRepository;
        this.gymService = gymService;
    }

    public Optional<List<Member>> findAllByTrainer(UUID id) {
        return trainerRepository.find(id)
                .map(memberRepository::findAllByTrainer);
    }

    public Optional<List<Member>> findAllByGym(UUID id){
        return gymRepository.find(id)
                .map(memberRepository::findAllByGym);
    }

    public Optional<Member> find(UUID id) { return memberRepository.find(id); }

    public Optional<Member> find(String name) { return  memberRepository.findByName(name); }

    public List<Member> findAll() { return memberRepository.findAll(); }

    public void create(Member member, UUID trainerId, UUID gymId) {
        Trainer trainer = trainerService.find(trainerId).orElseThrow(() -> new NotFoundException("Trainer not found: " + trainerId));

        Gym gym = gymService.find(gymId).orElseThrow(() -> new NotFoundException("Gym not found: " + gymId));

        memberRepository.create(member);

        if(trainer.getMembers() == null)
            trainer.setMembers(new ArrayList<>());
        List<Member> trainerMembers = new ArrayList<>(trainer.getMembers());
        trainerMembers.add(member);
        trainer.setMembers(trainerMembers);
        trainerService.update(trainer);

        if(gym.getMembers() == null)
            gym.setMembers(new ArrayList<>());
        List<Member> gymMembers = new ArrayList<>(gym.getMembers());
        gymMembers.add(member);
        gym.setMembers(gymMembers);
        gymService.update(gym);
    }

    public void update(Member member, UUID initialGym) {
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

        newGym.getMembers().removeIf(gymMember -> gymMember.getId().equals(member.getId()));
        newGym.getMembers().add(member);
        gymService.update(newGym);


        boolean trainerMemberUpdated = trainer.getMembers().removeIf(trainerMember -> trainerMember.getId().equals(member.getId()));
        if (trainerMemberUpdated) {
            trainer.getMembers().add(member);
        } else {
            throw new NotFoundException("Member not found in trainer's members: " + member.getId());
        }



        trainerService.update(trainer);
        memberRepository.update(member);
    }

    public void delete(UUID id)
    {
        Member member = memberRepository.find(id).orElseThrow(NotFoundException::new);

        Trainer trainer = trainerService.find(member.getTrainer().getId())
                .orElseThrow(() -> new NotFoundException("Trainer not found: " + member.getTrainer().getId()));

        trainer.getMembers().removeIf(m -> m.getId().equals(member.getId()));

        Gym gym = gymService.find(member.getGym().getId())
                .orElseThrow(() -> new NotFoundException("Gym not found: " + member.getGym().getId()));

        gym.getMembers().removeIf(m -> m.getId().equals(member.getId()));
        trainerService.update(trainer);
        gymService.update(gym);
        memberRepository.delete(member);

    }



}

