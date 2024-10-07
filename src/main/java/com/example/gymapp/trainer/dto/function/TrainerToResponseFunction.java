package com.example.gymapp.trainer.dto.function;

import com.example.gymapp.trainer.dto.GetTrainerResponse;
import com.example.gymapp.trainer.entity.Trainer;

import java.util.function.Function;

public class TrainerToResponseFunction implements Function<Trainer, GetTrainerResponse> {

    @Override
    public GetTrainerResponse apply(Trainer user) {
        return GetTrainerResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .name(user.getName())
                .birthDate(user.getBirthDate())
                .build();
    }

}
