package com.example.gymapp.trainer.service;

import com.example.gymapp.member.entity.Member;
import com.example.gymapp.trainer.entity.Trainer;
import com.example.gymapp.trainer.repository.api.TrainerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
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

    @Transactional
    public void create(Trainer trainer){
        repository.create(trainer);
    }

    @Transactional
    public void update(Trainer trainer){
        repository.update(trainer);
    }

    @Transactional
    public void delete(UUID id){
        repository.delete(repository.find(id).orElseThrow(() -> new NotFoundException("Trainer not found")));
    }

    public void createAvatar(UUID id, InputStream is) {
        repository.find(id).ifPresent(trainer -> {
            try {
                if (trainer.getAvatar() != null) {
                    throw new IllegalArgumentException("Already exists");
                }
                trainer.setAvatar(is.readAllBytes());
                repository.update(trainer);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    public void deleteAvatar(UUID id) {
        repository.find(id).ifPresent(trainer -> {
                trainer.setAvatar(null);
                repository.update(trainer);
        });
    }

    public void updateAvatar(UUID id, InputStream is) {
        repository.find(id).ifPresent(trainer -> {
            try {
                byte[] newAvatar = is.readAllBytes();
                if (Arrays.equals(trainer.getAvatar(), newAvatar)) {
                    throw new IllegalArgumentException("The same avatar already exists.");
                }
                trainer.setAvatar(newAvatar);
                repository.update(trainer);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }



}

