package com.example.gymapp.trainer.dto.function;

import com.example.gymapp.trainer.dto.GetTrainersResponse;
import com.example.gymapp.trainer.entity.Trainer;

import java.util.List;
import java.util.function.Function;

public class TrainersToResponseFunction implements Function<List<Trainer>, GetTrainersResponse> {

    @Override
    public GetTrainersResponse apply(List<Trainer> trainers) {
        return GetTrainersResponse.builder()
                .trainers(trainers.stream()
                        .map(trainer -> GetTrainersResponse.Trainer.builder()
                                .id(trainer.getId())
                                .login(trainer.getLogin())
                                .build())
                        .toList())
                .build();
    }

}

