package com.example.gymapp.trainer.dto.function;

import com.example.gymapp.trainer.dto.PutPasswordRequest;
import com.example.gymapp.trainer.entity.Trainer;

import java.util.function.BiFunction;

public class UpdateTrainerPasswordWithRequestFunction implements BiFunction<Trainer, PutPasswordRequest, Trainer> {

    @Override
    public Trainer apply(Trainer entity, PutPasswordRequest request) {
        return Trainer.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .name(entity.getName())
                .birthDate(entity.getBirthDate())
                .password(request.getPassword())
                .build();

    }

}

