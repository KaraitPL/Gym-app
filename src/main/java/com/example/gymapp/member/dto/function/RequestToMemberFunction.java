package com.example.gymapp.member.dto.function;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.member.dto.PutMemberRequest;
import com.example.gymapp.member.entity.Member;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToMemberFunction implements BiFunction<UUID, PutMemberRequest, Member> {

    @Override
    public Member apply(UUID id, PutMemberRequest request) {
        return Member.builder()
                .id(id)
                .name(request.getName())
                .age(request.getAge())
                .gym(Gym.builder()
                        .id(request.getGym())
                        .build())
                .build();
    }

}

