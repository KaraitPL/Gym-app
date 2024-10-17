package com.example.gymapp.gym.repository.memory;

import com.example.gymapp.datastore.component.DataStore;
import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.gym.repository.api.GymRepository;
import com.example.gymapp.member.entity.Member;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GymInMemoryRepository implements GymRepository {
    private final DataStore store;
    public GymInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Gym> find(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Gym> findAll() {
        return List.of();
    }

    @Override
    public void create(Gym entity) {

    }
    @Override
    public void delete(Gym entity) {

    }
    @Override
    public void update(Gym entity) {

    }
}
