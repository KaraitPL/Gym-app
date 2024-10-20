package com.example.gymapp.trainer.controller.api;

import com.example.gymapp.trainer.dto.GetTrainerResponse;
import com.example.gymapp.trainer.dto.GetTrainersResponse;
import com.example.gymapp.trainer.dto.PatchTrainerRequest;
import com.example.gymapp.trainer.dto.PutTrainerRequest;

import java.io.InputStream;
import java.util.UUID;

public interface TrainerController {

    GetTrainerResponse getTrainer(UUID id);

    GetTrainersResponse getTrainers();

    void putTrainer(UUID id, PutTrainerRequest request);

    void patchTrainer(UUID id, PatchTrainerRequest request);

    void deleteTrainer(UUID id);

    byte[] getTrainerAvatar(UUID id);

    void putTrainerAvatar(UUID id, InputStream avatar);

    void deleteTrainerAvatar(UUID id);

    void patchTrainerAvatar(UUID id, InputStream avatar);

}
