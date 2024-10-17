package com.example.gymapp.member.dto.function;

import com.example.gymapp.member.dto.PatchMemberRequest;
import com.example.gymapp.member.entity.Member;

import java.util.UUID;
import java.util.function.BiFunction;

public class UpdateMemberWithRequestFunction implements BiFunction<Member, PatchMemberRequest, Member> {

    @Override
    public Member apply(Member entity, PatchMemberRequest request) {
        return Member.builder()
                .id(entity.getId())
                .name(request.getName())
                .benchPressMax(request.getBenchPressMax())
                .gym(entity.getGym())
                .trainer(entity.getTrainer())
                .build();
    }
}
