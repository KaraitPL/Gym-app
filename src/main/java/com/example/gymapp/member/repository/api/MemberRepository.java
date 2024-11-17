package com.example.gymapp.member.repository.api;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.repository.api.Repository;
import com.example.gymapp.trainer.entity.Trainer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends Repository<Member, UUID> {

    List<Member> findAllByTrainer(Trainer trainer);

    List<Member> findAllByGym(Gym gym);

/*    Optional<Member> findByName(String name);*/

}

