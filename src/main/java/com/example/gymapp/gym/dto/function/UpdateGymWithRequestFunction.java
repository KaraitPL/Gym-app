package com.example.gymapp.gym.dto.function;

import com.example.gymapp.gym.dto.PatchGymRequest;
import com.example.gymapp.gym.entity.Gym;

import java.util.function.BiFunction;

public class UpdateGymWithRequestFunction implements BiFunction<Gym, PatchGymRequest, Gym> {
    @Override
    public Gym apply(Gym entity, PatchGymRequest request) {
        return Gym.builder()
                .id(entity.getId())
                .name(request.getName())
                .numberOfEquipment(entity.getNumberOfEquipment())
                .build();
    }
}
