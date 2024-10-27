package com.example.gymapp.gym.view;

import com.example.gymapp.component.ModelFunctionFactory;
import com.example.gymapp.gym.model.GymsModel;
import com.example.gymapp.gym.service.GymService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
@Named
public class GymList {
    private final GymService gymService;

    private final ModelFunctionFactory factory;

    private GymsModel gyms;


    @Inject
    public GymList(GymService gymService, ModelFunctionFactory factory) {
        this.gymService = gymService;
        this.factory = factory;
    }

    public GymsModel getGyms() {
        if (gyms == null) {
            gyms = factory.gymsToModel().apply(gymService.findAll());
        }
        return gyms;
    }

    public String deleteAction(GymsModel.Gym gym) {
        gymService.delete(gym.getId());
        return "gym_list?faces-redirect=true";
    }

}
