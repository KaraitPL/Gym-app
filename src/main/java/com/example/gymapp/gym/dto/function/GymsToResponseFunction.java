package com.example.gymapp.gym.dto.function;

import com.example.gymapp.gym.dto.GetGymsResponse;
import com.example.gymapp.gym.entity.Gym;

import java.util.List;
import java.util.function.Function;

public class GymsToResponseFunction implements Function<List<Gym>, GetGymsResponse> {

    @Override
    public GetGymsResponse apply(List<Gym> entities) {
        return GetGymsResponse.builder()
                .gyms(entities.stream()
                        .map(gym -> GetGymsResponse.Gym.builder()
                                .id(gym.getId())
                                .name(gym.getName())
                                .numberOfEquipment(gym.getNumberOfEquipment())
                                .build())
                        .toList())
                .build();
    }
}
