package com.example.gymapp.trainer.controller.simple;

import com.example.gymapp.component.DtoFunctionFactory;
import com.example.gymapp.controller.servlet.exception.NotFoundException;
import com.example.gymapp.trainer.controller.api.TrainerController;
import com.example.gymapp.trainer.dto.GetTrainerResponse;
import com.example.gymapp.trainer.dto.GetTrainersResponse;
import com.example.gymapp.trainer.service.TrainerService;

import java.util.UUID;

public class TrainerSimpleController implements TrainerController {

    private final TrainerService service;

    private final DtoFunctionFactory factory;

    public TrainerSimpleController(TrainerService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }
    @Override
    public GetTrainerResponse getTrainer(UUID uuid){
        return service.find(uuid)
                .map(factory.trainerToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetTrainersResponse getTrainers(){
        return factory.trainersToResponse().apply(service.findAll());
    }
}
