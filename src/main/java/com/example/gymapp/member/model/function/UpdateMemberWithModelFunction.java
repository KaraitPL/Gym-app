package com.example.gymapp.member.model.function;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.member.model.MemberEditModel;
import com.example.gymapp.trainer.entity.Trainer;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateMemberWithModelFunction implements BiFunction<Member, MemberEditModel, Member>, Serializable {

    @Override
    @SneakyThrows
    public Member apply(Member member, MemberEditModel request) {
        return Member.builder()
                .id(member.getId())
                .name(request.getName())
                .benchPressMax(request.getBenchPressMax())
                .trainer(Trainer.builder()
                        .id(request.getTrainer().getId())
                        .build())
                .gym(Gym.builder()
                        .id(request.getGym().getId())
                        .name(request.getGym().getName())
                        .numberOfEquipment(request.getGym().getNumberOfEquipment())
                        .gymType(request.getGym().getGymType())
                        .members(request.getGym().getMembers())
                        .build())
                .version(request.getVersion())
                .build();
    }
}
