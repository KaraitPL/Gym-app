package com.example.gymapp.member.service;

import com.example.gymapp.controller.servlet.exception.NotFoundException;
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

    public MemberService(MemberRepository memberRepository, TrainerRepository trainerRepository, GymRepository gymRepository) {
        this.memberRepository = memberRepository;
        this.trainerRepository = trainerRepository;
        this.gymRepository = gymRepository;
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

    public void create(Member member) { memberRepository.create(member);}

    public void update(Member member) { memberRepository.update(member); }

    public void delete(UUID id) { memberRepository.find(id).orElseThrow(NotFoundException::new); }



}

