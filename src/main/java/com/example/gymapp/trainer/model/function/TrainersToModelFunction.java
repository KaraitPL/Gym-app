package com.example.gymapp.trainer.model.function;

import com.example.gymapp.trainer.entity.Trainer;
import com.example.gymapp.trainer.model.TrainersModel;

import java.util.List;
import java.util.function.Function;

public class TrainersToModelFunction implements Function<List<Trainer>, TrainersModel> {
    @Override
    public TrainersModel apply(List<Trainer> entity) {
        return TrainersModel.builder()
                .trainers(entity.stream()
                        .map(Trainer -> TrainersModel.Trainer.builder()
                                .id(Trainer.getId())
                                .name(Trainer.getName())
                                .build())
                        .toList())
                .build();
    }
}