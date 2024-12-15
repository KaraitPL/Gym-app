package com.example.gymapp.member.model.function;

import com.example.gymapp.gym.model.GymModel;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.member.model.MemberEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class MemberToEditModelFunction implements Function<Member, MemberEditModel>, Serializable {

    @Override
    public MemberEditModel apply(Member member) {
        return MemberEditModel.builder()
                .name(member.getName())
                .benchPressMax(member.getBenchPressMax())
                .trainer(member.getTrainer())
                .gym(GymModel.builder()
                        .id(member.getGym().getId())
                        .name(member.getGym().getName())
                        .numberOfEquipment(member.getGym().getNumberOfEquipment())
                        .gymType(member.getGym().getGymType())
                        .members(member.getGym().getMembers())
                        .build())
                .version(member.getVersion())
                .build();
    }
}
