package com.example.gymapp.trainer.dto.function;

import com.example.gymapp.trainer.dto.PutTrainerRequest;
import com.example.gymapp.trainer.entity.Trainer;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToTrainerFunction implements BiFunction<UUID, PutTrainerRequest, Trainer> {

    @Override
    public Trainer apply(UUID id, PutTrainerRequest request) {
        return Trainer.builder()
                .id(id)
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .password(request.getPassword())
                .login(request.getLogin())
                .build();
    }

}

