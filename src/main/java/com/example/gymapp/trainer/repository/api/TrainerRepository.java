package com.example.gymapp.trainer.repository.api;

import com.example.gymapp.repository.api.Repository;
import com.example.gymapp.trainer.entity.Trainer;

import java.util.Optional;
import java.util.UUID;

public interface TrainerRepository extends Repository<Trainer, UUID> {
    Optional<Trainer> findByLogin(String login);

}

