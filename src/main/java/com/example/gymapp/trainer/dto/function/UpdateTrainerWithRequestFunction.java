package com.example.gymapp.trainer.dto.function;

import com.example.gymapp.trainer.dto.PatchTrainerRequest;
import com.example.gymapp.trainer.entity.Trainer;

import java.util.function.BiFunction;

public class UpdateTrainerWithRequestFunction implements BiFunction<Trainer, PatchTrainerRequest, Trainer> {

    @Override
    public Trainer apply(Trainer entity, PatchTrainerRequest request) {
        return Trainer.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .password(entity.getPassword())
                .build();
    }

}

