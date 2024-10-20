package com.example.gymapp.gym.repository.memory;

import com.example.gymapp.datastore.component.DataStore;
import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.gym.repository.api.GymRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class GymInMemoryRepository implements GymRepository {
    private final DataStore store;
    @Inject
    public GymInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Gym> findByName(String name) {
        return store.findAllGyms().stream()
            .filter(gym -> gym.getName().equals(name))
            .findFirst();
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
        store.deleteGym(entity.getId());
    }
    @Override
    public void update(Gym entity) {
        store.updateGym(entity);
    }

}
