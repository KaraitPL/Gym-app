package com.example.gymapp.gym.service;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.gym.repository.api.GymRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GymService {

    private final GymRepository repository;

    public GymService(GymRepository repository) {
        this.repository = repository;
    }

    public Optional<Gym> find(UUID id) {
        return repository.find(id);
    }

    public List<Gym> findAll() {
        return repository.findAll();
    }

    public void create(Gym gym) {
        repository.create(gym);
    }

}

