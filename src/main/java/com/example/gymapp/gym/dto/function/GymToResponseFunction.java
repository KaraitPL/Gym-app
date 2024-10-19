package com.example.gymapp.gym.dto.function;

import com.example.gymapp.gym.dto.GetGymResponse;
import com.example.gymapp.gym.entity.Gym;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GymToResponseFunction implements Function<Gym, GetGymResponse> {
    @Override
    public GetGymResponse apply(Gym entity) {
        return GetGymResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .numberOfEquipment(entity.getNumberOfEquipment())
                .gymType(entity.getGymType())
                /*.members(Optional.ofNullable(entity.getMembers())
                        .orElse(Collections.emptyList())  // Zwraca pustą listę, jeśli members jest null
                        .stream()
                        .map(member -> GetGymResponse.Member.builder()
                                .id(member.getId())
                                .name(member.getName())
                                .benchPressMax(member.getBenchPressMax())
                                .build())
                        .collect(Collectors.toList()))*/
                .build();
    }
}
