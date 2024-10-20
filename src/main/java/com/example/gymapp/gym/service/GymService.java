package com.example.gymapp.gym.service;

import com.example.gymapp.controller.servlet.exception.NotFoundException;
import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.gym.repository.api.GymRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
@NoArgsConstructor(force = true)
public class GymService {
    private final GymRepository repository;

    @Inject
    public GymService(GymRepository gymRepository) { this.repository = gymRepository; }

    public Optional<Gym> find(UUID id) { return repository.find(id); }

    public Optional<Gym> find(String name) { return repository.findByName(name); }

    public List<Gym> findAll() { return repository.findAll(); }

    public void create(Gym gym) { repository.create(gym); }

    public void delete(UUID id) { repository.delete(repository.find(id).orElseThrow(NotFoundException::new)); }

    public void update(Gym gym) { repository.update(gym); }

}
