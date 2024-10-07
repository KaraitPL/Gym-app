package com.example.gymapp.gym.repository.memory;

import com.example.gymapp.datastore.component.DataStore;
import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.gym.repository.api.GymRepository;

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
        return store.findAllGyms().stream()
                .filter(gym -> gym.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Gym> findAll() {
        return store.findAllGyms();
    }

    @Override
    public void create(Gym entity) {
        store.createGym(entity);
    }

    @Override
    public void delete(Gym entity) {
        throw new UnsupportedOperationException("Operation not implemented.");
    }

    @Override
    public void update(Gym entity) {
        throw new UnsupportedOperationException("Operation not implemented.");
    }

}

