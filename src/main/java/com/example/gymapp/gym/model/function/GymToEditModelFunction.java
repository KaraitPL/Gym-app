package com.example.gymapp.gym.model.function;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.gym.model.GymEditModel;

import java.util.function.Function;

public class GymToEditModelFunction implements Function<Gym, GymEditModel> {
    @Override
    public GymEditModel apply(Gym gym) {
        return GymEditModel.builder()
                .name(gym.getName())
                .numberOfEquipment(gym.getNumberOfEquipment())
                .gymType(gym.getGymType())
                .build();
    }
}
