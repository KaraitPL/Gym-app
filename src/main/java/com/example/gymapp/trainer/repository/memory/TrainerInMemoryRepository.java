package com.example.gymapp.trainer.repository.memory;

import com.example.gymapp.datastore.component.DataStore;
import com.example.gymapp.trainer.entity.Trainer;
import com.example.gymapp.trainer.repository.api.TrainerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TrainerInMemoryRepository implements TrainerRepository {

    private final DataStore store;

    public TrainerInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Trainer> find(UUID id) {
        return store.findAllTrainers().stream()
                .filter(trainer -> trainer.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Trainer> findAll() {
        return store.findAllTrainers();
    }

    @Override
    public void create(Trainer entity) {
        store.createTrainer(entity);
    }

    @Override
    public void delete(Trainer entity) {
        store.deleteTrainer(entity.getId());
    }

    @Override
    public void update(Trainer entity) {
        store.updateTrainer(entity);
    }

    @Override
    public Optional<Trainer> findByName(String name) {
        return store.findAllTrainers().stream()
                .filter(trainer -> trainer.getName().equals(name))
                .findFirst();
    }

}