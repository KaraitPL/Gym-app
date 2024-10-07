package com.example.gymapp.trainer.service;

import com.example.gymapp.crypto.component.Pbkdf2PasswordHash;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.trainer.entity.Trainer;
import com.example.gymapp.trainer.repository.api.TrainerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TrainerService {

    private final TrainerRepository repository;

    private final Pbkdf2PasswordHash passwordHash;

    public TrainerService(TrainerRepository repository, Pbkdf2PasswordHash passwordHash) {
        this.repository = repository;
        this.passwordHash = passwordHash;
    }

    public Optional<Trainer> find(UUID id) {
        return repository.find(id);
    }

    public Optional<Trainer> find(String login) {
        return repository.findByLogin(login);
    }

    public List<Trainer> findAll() {
        return repository.findAll();
    }

    public void create(Trainer trainer) {
        trainer.setPassword(passwordHash.generate(trainer.getPassword().toCharArray()));
        repository.create(trainer);
    }

    public boolean verify(String login, String password) {
        return find(login)
                .map(trainer -> passwordHash.verify(password.toCharArray(), trainer.getPassword()))
                .orElse(false);
    }

}

