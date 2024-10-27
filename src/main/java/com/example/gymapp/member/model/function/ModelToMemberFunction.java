package com.example.gymapp.member.model.function;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.member.model.MemberCreateModel;

import java.io.Serializable;
import java.util.function.Function;

public class ModelToMemberFunction implements Function<MemberCreateModel, Member>, Serializable {
    @Override
    public Member apply(MemberCreateModel model) {
        return Member.builder()
                .id(model.getId())
                .name(model.getName())
                .benchPressMax(model.getBenchPressMax())
                .trainer(model.getTrainer())
                .gym(Gym.builder()
                        .id(model.getGym().getId())
                        .name(model.getGym().getName())
                        .numberOfEquipment(model.getGym().getNumberOfEquipment())
                        .gymType(model.getGym().getGymType())
                        .members(model.getGym().getMembers())
                        .build())
                .build();
    }
}
