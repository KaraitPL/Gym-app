package com.example.gymapp.trainer.controller.simple;

import com.example.gymapp.component.DtoFunctionFactory;
import com.example.gymapp.controller.servlet.exception.BadRequestException;
import com.example.gymapp.controller.servlet.exception.NotFoundException;
import com.example.gymapp.trainer.controller.api.TrainerController;
import com.example.gymapp.trainer.dto.GetTrainerResponse;
import com.example.gymapp.trainer.dto.GetTrainersResponse;
import com.example.gymapp.trainer.dto.PatchTrainerRequest;
import com.example.gymapp.trainer.dto.PutTrainerRequest;
import com.example.gymapp.trainer.service.TrainerService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

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
                .orElseThrow(NotFoundException::new);
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
            throw new BadRequestException(ex);
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
    public byte[] getTrainerAvatar(UUID id, String pathToAvatars) {
        Path pathToAvatar = Paths.get(
                pathToAvatars,
                trainerService.find(id)
                        .map(trainer -> trainer.getId().toString())
                        .orElseThrow(() -> new NotFoundException("Trainer does not exist"))
                        + ".png"
        );
        try {
            if (!Files.exists(pathToAvatar)) {
                throw new NotFoundException("Trainer avatar does not exist");
            }
            return Files.readAllBytes(pathToAvatar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putTrainerAvatar(UUID id, InputStream avatar, String pathToAvatars) {
        trainerService.find(id).ifPresentOrElse(
                trainer -> {
                    trainerService.createAvatar(id, avatar, pathToAvatars);
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteTrainerAvatar(UUID id, String pathToAvatars) {
        trainerService.find(id).ifPresentOrElse(
                trainer -> {
                    try {
                        Path avatarPath = Paths.get(pathToAvatars, trainer.getId().toString() + ".png");
                        if (!Files.exists(avatarPath)) {
                            throw new NotFoundException("Trainer avatar does not exist");
                        }
                        Files.delete(avatarPath);
                    } catch (IOException e) {
                        throw new NotFoundException(e);
                    }
                },
                () -> {
                    throw new NotFoundException("Trainer does not exist");
                }
        );
    }

    @Override
    public void patchTrainerAvatar(UUID id, InputStream avatar, String pathToAvatars) {
        trainerService.find(id).ifPresentOrElse(
                trainer -> trainerService.updateAvatar(id, avatar, pathToAvatars),
                () -> {
                    throw new NotFoundException("Trainer does not exist");
                }
        );
    }

}
