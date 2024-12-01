package com.example.gymapp.trainer.controller.rest;

import com.example.gymapp.component.DtoFunctionFactory;
import com.example.gymapp.trainer.controller.api.TrainerController;
import com.example.gymapp.trainer.dto.GetTrainerResponse;
import com.example.gymapp.trainer.dto.GetTrainersResponse;
import com.example.gymapp.trainer.dto.PatchTrainerRequest;
import com.example.gymapp.trainer.dto.PutTrainerRequest;
import com.example.gymapp.trainer.entity.TrainerRoles;
import com.example.gymapp.trainer.service.TrainerService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
@RolesAllowed(TrainerRoles.USER)
public class TrainerRestController implements TrainerController {

    private TrainerService trainerService;

    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public TrainerRestController(DtoFunctionFactory factory, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setTrainerService(TrainerService trainerService) {
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

    @PermitAll
    @Override
    public void putTrainer(UUID id, PutTrainerRequest request) {
        try {
            trainerService.create(factory.requestToTrainer().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(TrainerController.class, "getTrainer")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (EJBException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException("Trainer already exists, to update trainer use PATCH method");
            }
            throw ex;
        }
    }

    @Override
    public void patchTrainer(UUID id, PatchTrainerRequest request) {
        trainerService.find(id)
                .ifPresentOrElse(entity -> trainerService.update(factory.updateTrainer().apply(entity, request)), () -> {
                    throw new NotFoundException("Trainer not found");
                });

    }

    @Override
    public void deleteTrainer(UUID id) {
        trainerService.find(id).ifPresentOrElse(entity -> trainerService.delete(id), () -> {
            throw new NotFoundException("Trainer not found");
        });
    }

    @Override
    public byte[] getTrainerAvatar(UUID id, String pathToAvatars) {

        java.nio.file.Path pathToAvatar = Paths.get(
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
                        java.nio.file.Path avatarPath = Paths.get(pathToAvatars, trainer.getId().toString() + ".png");
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
