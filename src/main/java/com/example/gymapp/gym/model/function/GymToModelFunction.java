package com.example.gymapp.gym.model.function;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.gym.model.GymModel;

import java.io.Serializable;
import java.util.function.Function;

public class GymToModelFunction implements Function<Gym, GymModel>, Serializable {

    @Override
    public GymModel apply(Gym gym) {
        return GymModel.builder()
                .id(gym.getId())
                .name(gym.getName())
                .numberOfEquipment(gym.getNumberOfEquipment())
                .gymType(gym.getGymType())
                .members(gym.getMembers())
                .build();
    }
}
