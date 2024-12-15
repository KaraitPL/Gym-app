package com.example.gymapp.member.model.function;

import com.example.gymapp.member.entity.Member;
import com.example.gymapp.member.model.MemberModel;

import java.io.Serializable;
import java.util.function.Function;

public class MemberToModelFunction implements Function<Member, MemberModel>, Serializable {

    @Override
    public MemberModel apply(Member member) {
        return MemberModel.builder()
                .id(member.getId())
                .name(member.getName())
                .benchPressMax(member.getBenchPressMax())
                .version(member.getVersion())
                .creationDateTime(member.getCreationDateTime())
                .build();
    }
}
