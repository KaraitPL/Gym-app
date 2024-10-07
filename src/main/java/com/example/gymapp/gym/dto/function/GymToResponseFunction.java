package com.example.gymapp.gym.dto.function;

import com.example.gymapp.gym.dto.GetGymResponse;
import com.example.gymapp.gym.dto.GetGymsResponse;
import com.example.gymapp.gym.entity.Gym;

import java.util.function.Function;

public class GymToResponseFunction implements Function<Gym, GetGymResponse> {

    @Override
    public GetGymResponse apply(Gym entity) {
        return GetGymResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .numberOfEquipment(entity.getNumberOfEquipment())
                .build();
    }

}

