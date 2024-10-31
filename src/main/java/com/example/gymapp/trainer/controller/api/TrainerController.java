package com.example.gymapp.trainer.controller.api;

import com.example.gymapp.trainer.dto.GetTrainerResponse;
import com.example.gymapp.trainer.dto.GetTrainersResponse;
import com.example.gymapp.trainer.dto.PatchTrainerRequest;
import com.example.gymapp.trainer.dto.PutTrainerRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.io.InputStream;
import java.util.UUID;

public interface TrainerController {

    @GET
    @Path("/trainers/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetTrainerResponse getTrainer(@PathParam("id") UUID id);

    @GET
    @Path("/trainers")
    @Produces(MediaType.APPLICATION_JSON)
    GetTrainersResponse getTrainers();

    @PUT
    @Path("/trainers/{id}")
    void putTrainer(@PathParam("id") UUID id, PutTrainerRequest request);

    @PATCH
    @Path("/trainers/{id}")
    void patchTrainer(@PathParam("id") UUID id, PatchTrainerRequest request);

    @DELETE
    @Path("/trainers/{id}")
    void deleteTrainer(@PathParam("id") UUID id);

    byte[] getTrainerAvatar(UUID id);

    void putTrainerAvatar(UUID id, InputStream avatar);

    void deleteTrainerAvatar(UUID id);

    void patchTrainerAvatar(UUID id, InputStream avatar);

}
