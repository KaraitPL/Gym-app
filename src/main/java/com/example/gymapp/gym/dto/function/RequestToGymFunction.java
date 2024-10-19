package com.example.gymapp.gym.dto.function;

import com.example.gymapp.gym.dto.PutGymRequest;
import com.example.gymapp.gym.entity.Gym;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToGymFunction implements BiFunction<UUID, PutGymRequest, Gym> {
    @Override
    public Gym apply(UUID id, PutGymRequest request) {
        return Gym.builder()
                .id(id)
                .name(request.getName())
                .gymType(request.getGymType())
                .numberOfEquipment(request.getNumberOfEquipment())
                .build();
    }
}
