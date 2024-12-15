package com.example.gymapp.member.model.function;

import com.example.gymapp.member.entity.Member;
import com.example.gymapp.member.model.MembersModel;

import java.util.List;
import java.util.function.Function;

public class MembersToModelFunction implements Function<List<Member>, MembersModel>{
    @Override
    public MembersModel apply(List<Member> members) {
        return MembersModel.builder()
                .members(members.stream()
                        .map(member->MembersModel.Member.builder()
                                .id(member.getId())
                                .name(member.getName())
                                .benchPressMax(member.getBenchPressMax())
                                .version(member.getVersion())
                                .creationDateTime(member.getCreationDateTime())
                                .modifiedDateTime(member.getEditDateTime())
                                .build())
                        .toList()
                )
                .build();
    }
}
