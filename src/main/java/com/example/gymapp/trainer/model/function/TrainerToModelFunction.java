package com.example.gymapp.trainer.model.function;

import com.example.gymapp.trainer.entity.Trainer;
import com.example.gymapp.trainer.model.TrainerModel;

import java.io.Serializable;
import java.util.function.Function;

public class TrainerToModelFunction implements Function<Trainer, TrainerModel>, Serializable {
    @Override
    public TrainerModel apply(Trainer trainer) {
        return TrainerModel.builder()
                .id(trainer.getId())
                .name(trainer.getName())
                .build();
    }
}
