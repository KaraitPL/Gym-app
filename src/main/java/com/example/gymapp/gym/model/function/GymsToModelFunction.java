package com.example.gymapp.gym.model.function;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.gym.model.GymModel;
import com.example.gymapp.gym.model.GymsModel;

import java.util.List;
import java.util.function.Function;

public class GymsToModelFunction implements Function<List<Gym>, GymsModel> {
    @Override
    public GymsModel apply(List<Gym> gyms) {
        return GymsModel.builder()
                .gyms(gyms.stream()
                        .map(gym -> GymsModel.Gym.builder()
                                .id(gym.getId())
                                .name(gym.getName())
                                .numberOfEquipment(gym.getNumberOfEquipment())
                                .build())
                        .toList())
                .build();
    }
}
