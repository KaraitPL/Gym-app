package com.example.gymapp.trainer.view;

import com.example.gymapp.component.ModelFunctionFactory;
import com.example.gymapp.trainer.model.TrainersModel;
import com.example.gymapp.trainer.service.TrainerService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.NoArgsConstructor;

@RequestScoped
@Named
@NoArgsConstructor(force = true)
public class TrainerList {

    private final TrainerService service;
    private TrainersModel trainers;

    private final ModelFunctionFactory factory;

    @Inject
    public TrainerList(TrainerService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }
    public TrainersModel getTrainers() {
        if (trainers == null) {
            trainers = factory.trainersToModel().apply(service.findAll());
        }
        return trainers;
    }
    public String deleteAction(TrainersModel.Trainer trainer) {
        service.delete(trainer.getId());
        return "trainer_list?faces-redirect=true";
    }
}
