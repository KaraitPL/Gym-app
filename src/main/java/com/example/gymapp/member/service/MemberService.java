package com.example.gymapp.member.service;

import com.example.gymapp.member.entity.Member;
import com.example.gymapp.member.repository.api.MemberRepository;
import com.example.gymapp.trainer.entity.Trainer;
import com.example.gymapp.trainer.repository.api.TrainerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MemberService {

    private final MemberRepository memberRepository;

    private final TrainerRepository trainerRepository;

    public MemberService(MemberRepository memberRepository, TrainerRepository trainerRepository) {
        this.memberRepository = memberRepository;
        this.trainerRepository = trainerRepository;
    }

    public Optional<List<Member>> findAllByTrainer(UUID id) {
        return trainerRepository.find(id)
                .map(memberRepository::findAllByTrainer);
    }


    public void create(Member member1) {
    }
}

