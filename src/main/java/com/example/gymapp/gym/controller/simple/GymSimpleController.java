package com.example.gymapp.gym.controller.simple;

import com.example.gymapp.component.DtoFunctionFactory;
import com.example.gymapp.gym.controller.api.GymController;
import com.example.gymapp.gym.dto.GetGymsResponse;
import com.example.gymapp.gym.service.GymService;

public class GymSimpleController implements GymController {

    private final GymService service;

    private final DtoFunctionFactory factory;

    public GymSimpleController(GymService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetGymsResponse getGyms() {
        return factory.gymsToResponse().apply(service.findAll());
    }

}

