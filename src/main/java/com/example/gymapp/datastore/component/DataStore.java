package com.example.gymapp.datastore.component;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.serialization.component.CloningUtility;
import com.example.gymapp.trainer.entity.Trainer;
import lombok.extern.java.Log;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Log
public class DataStore {

    private final Set<Trainer> trainers = new HashSet<>();

    private final Set<Member> Members = new HashSet<>();

    private final CloningUtility cloningUtility;

    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    public synchronized List<Trainer> findAllTrainers() {
        return trainers.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized List<Member> findAllMembers() {
        return Members.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createTrainer(Trainer value) throws IllegalArgumentException {
        if (trainers.stream().anyMatch(trainer -> trainer.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The trainer id \"%s\" is not unique".formatted(value.getId()));
        }
        trainers.add(cloningUtility.clone(value));
    }

    public synchronized void updateTrainer(Trainer value) throws IllegalArgumentException {
        if (trainers.removeIf(trainer -> trainer.getId().equals(value.getId()))) {
            trainers.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The trainer with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deleteTrainer(UUID id) throws IllegalArgumentException {
        if (!trainers.removeIf(trainer -> trainer.getId().equals(id))) {
            throw new IllegalArgumentException("The trainer with id \"%s\" does not exist".formatted(id));
        }
    }

}

