package com.example.gymapp.member.dto.function;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.member.dto.PutMemberRequest;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.trainer.entity.Trainer;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

public class RequestToMemberFunction implements BiFunction<UUID, PutMemberRequest, Member> {
    @Override
    public Member apply(UUID id, PutMemberRequest request) {
        return Member.builder()
                .id(id)
                .name(request.getName())
                .benchPressMax(request.getBenchPressMax())
                .gym(Gym.builder()
                        .id(request.getGym())
                        .build())
                .trainer(Trainer.builder()
                        .id(request.getTrainer())
                        .build())
                .build();
    }
}
