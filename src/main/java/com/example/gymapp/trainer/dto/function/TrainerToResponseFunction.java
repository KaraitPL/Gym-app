package com.example.gymapp.trainer.dto.function;

import com.example.gymapp.trainer.dto.GetTrainerResponse;
import com.example.gymapp.trainer.entity.Trainer;

import java.util.function.Function;

public class TrainerToResponseFunction implements Function<Trainer, GetTrainerResponse> {

    @Override
    public GetTrainerResponse apply(Trainer trainer) {
        return GetTrainerResponse.builder()
                .id(trainer.getId())
                .name(trainer.getName())
                .yearsOfTraining(trainer.getYearsOfTraining())
                .birthDate(trainer.getBirthDate())
                .build();
    }

}
