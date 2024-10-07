package com.example.gymapp.member.dto.function;

import com.example.gymapp.member.dto.GetMembersResponse;
import com.example.gymapp.member.entity.Member;

import java.util.List;
import java.util.function.Function;

public class MembersToResponseFunction implements Function<List<Member>, GetMembersResponse> {

    @Override
    public GetMembersResponse apply(List<Member> entities) {
        return GetMembersResponse.builder()
                .members(entities.stream()
                        .map(member -> GetMembersResponse.Member.builder()
                                .id(member.getId())
                                .name(member.getName())
                                .build())
                        .toList())
                .build();
    }

}

