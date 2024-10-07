package com.example.gymapp.member.service;

import com.example.gymapp.gym.repository.api.GymRepository;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.member.repository.api.MemberRepository;
import com.example.gymapp.trainer.entity.Trainer;
import com.example.gymapp.trainer.repository.api.TrainerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MemberService {

    private final MemberRepository memberRepository;

    private final GymRepository gymRepository;

    private final TrainerRepository trainerRepository;

    public MemberService(MemberRepository memberRepository, GymRepository gymRepository, TrainerRepository trainerRepository) {
        this.memberRepository = memberRepository;
        this.gymRepository = gymRepository;
        this.trainerRepository = trainerRepository;
    }

    public Optional<Member> find(UUID id) {
        return memberRepository.find(id);
    }

    public Optional<Member> find(Trainer trainer, UUID id) {
        return memberRepository.findByIdAndTrainer(id, trainer);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public List<Member> findAll(Trainer trainer) {
        return memberRepository.findAllByTrainer(trainer);
    }

    public void create(Member member) {
        memberRepository.create(member);
    }

    public void update(Member member) {
        memberRepository.update(member);
    }

    public void delete(UUID id) {
        memberRepository.delete(memberRepository.find(id).orElseThrow());
    }

    public Optional<List<Member>> findAllByGym(UUID id) {
        return gymRepository.find(id)
                .map(memberRepository::findAllByGym);
    }

    public Optional<List<Member>> findAllByTrainer(UUID id) {
        return trainerRepository.find(id)
                .map(memberRepository::findAllByTrainer);
    }
}

