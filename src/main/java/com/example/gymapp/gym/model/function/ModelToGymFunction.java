package com.example.gymapp.gym.model.function;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.gym.model.GymModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

public class ModelToGymFunction implements Function<GymModel, Gym>, Serializable {
    @Override
    public Gym apply(GymModel gymModel) {
        return Gym.builder()
                .id(gymModel.getId())
                .name(gymModel.getName())
                .numberOfEquipment(gymModel.getNumberOfEquipment())
                .gymType(gymModel.getGymType())
                .members(new ArrayList<>())
                .build();
    }
}
