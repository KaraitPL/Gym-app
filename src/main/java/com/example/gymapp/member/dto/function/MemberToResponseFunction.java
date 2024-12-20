package com.example.gymapp.member.dto.function;

import com.example.gymapp.member.dto.GetMemberResponse;
import com.example.gymapp.member.entity.Member;

import java.util.function.Function;

public class MemberToResponseFunction implements Function<Member, GetMemberResponse> {
    @Override
    public GetMemberResponse apply(Member entity) {
        return GetMemberResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .benchPressMax(entity.getBenchPressMax())
                .build();
    }
}
