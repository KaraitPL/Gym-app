package com.example.gymapp.trainer.controller.simple;

import com.example.gymapp.component.DtoFunctionFactory;
import com.example.gymapp.trainer.controller.api.TrainerController;
import com.example.gymapp.trainer.dto.GetTrainerResponse;
import com.example.gymapp.trainer.dto.GetTrainersResponse;
import com.example.gymapp.trainer.dto.PatchTrainerRequest;
import com.example.gymapp.trainer.dto.PutTrainerRequest;
import com.example.gymapp.trainer.entity.Trainer;
import com.example.gymapp.trainer.service.TrainerService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequestScoped
public class TrainerSimpleController implements TrainerController {

    private final TrainerService trainerService;

    private final DtoFunctionFactory factory;

    @Inject
    public TrainerSimpleController(DtoFunctionFactory factory, TrainerService trainerService) {
        this.factory = factory;
        this.trainerService = trainerService;
    }

    @Override
    public GetTrainerResponse getTrainer(UUID id) {
        return trainerService.find(id)
                .map(factory.trainerToResponse())
                .orElseThrow(() -> new NotFoundException("Trainer not found"));
    }

    @Override
    public GetTrainersResponse getTrainers() {
        return factory.trainersToResponse().apply(trainerService.findAll());
    }

    @Override
    public void putTrainer(UUID id, PutTrainerRequest request) {
        try {
            trainerService.create(factory.requestToTrainer().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }

    }

    @Override
    public void patchTrainer(UUID id, PatchTrainerRequest request) {
        trainerService.find(id).ifPresentOrElse(entity -> trainerService.update(factory.updateTrainer().apply(entity, request)), () -> {
            throw new NotFoundException();
        });

    }

    @Override
    public void deleteTrainer(UUID id) {
        trainerService.find(id).ifPresentOrElse(
                entity -> trainerService.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public byte[] getTrainerAvatar(UUID id) {
        return trainerService.find(id)
                .map(Trainer::getAvatar)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putTrainerAvatar(UUID id, InputStream avatar) {
        trainerService.find(id).ifPresentOrElse(
                entity -> trainerService.createAvatar(id, avatar),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteTrainerAvatar(UUID id) {
        trainerService.find(id).ifPresentOrElse(
                entity -> trainerService.deleteAvatar(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void patchTrainerAvatar(UUID id, InputStream avatar) {
        trainerService.find(id).ifPresentOrElse(
                entity -> trainerService.updateAvatar(id, avatar),
                () -> {
                    throw new NotFoundException();
                }
        );
    }


}
