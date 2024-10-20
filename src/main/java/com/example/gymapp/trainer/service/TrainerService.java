package com.example.gymapp.trainer.service;

import com.example.gymapp.controller.servlet.exception.AlreadyExistsException;
import com.example.gymapp.controller.servlet.exception.NotFoundException;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.trainer.entity.Trainer;
import com.example.gymapp.trainer.repository.api.TrainerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class TrainerService {

    private final TrainerRepository repository;

    @Inject
    public TrainerService(TrainerRepository repository) {
        this.repository = repository;
    }

    public Optional<Trainer> find(UUID id){
        return repository.find(id);
    }

    public Optional<Trainer> find(String name){
        return repository.findByName(name);
    }

    public List<Trainer> findAll(){
        return repository.findAll();
    }

    public void create(Trainer trainer){
        repository.create(trainer);
    }

    public void update(Trainer trainer){
        repository.update(trainer);
    }

    public void delete(UUID id){
        repository.delete(repository.find(id).orElseThrow(NotFoundException::new));
    }

    public void createAvatar(UUID id, InputStream avatar, String pathToAvatars) throws AlreadyExistsException {
        repository.find(id).ifPresent(user -> {
            try {
                Path destinationPath = Path.of(pathToAvatars, id.toString() + ".png");
                if (Files.exists(destinationPath)) {
                    throw new AlreadyExistsException("Avatar already exists, to update avatar use PATCH method");
                }
                Files.copy(avatar, destinationPath);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });

    }

    public void updateAvatar(UUID id, InputStream avatar, String pathToAvatars) {
        repository.find(id).ifPresent(user -> {
            try {
                Path existingPath = Path.of(pathToAvatars, id.toString() + ".png");
                if (Files.exists(existingPath)) {
                    Files.copy(avatar, existingPath, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    throw new NotFoundException("User avatar not found, to create avatar use PUT method");
                }
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });

    }



}

