package com.example.gymapp.datastore.component;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.serialization.component.CloningUtility;
import com.example.gymapp.trainer.entity.Trainer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.util.*;
import java.util.stream.Collectors;

@Log
@ApplicationScoped
@NoArgsConstructor(force = true)
public class DataStore {

    private final Set<Trainer> trainers = new HashSet<>();

    private final Set<Member> members = new HashSet<>();
    private final Set<Gym> gyms = new HashSet<>();

    private final CloningUtility cloningUtility;

    @Inject
    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    public synchronized List<Trainer> findAllTrainers() {
        return trainers.stream()
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

    public synchronized List<Member> findAllMembers() {
        return members.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createMember(Member value) throws IllegalArgumentException {
        if (members.stream().anyMatch(member -> member.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The member id \"%s\" is not unique".formatted(value.getId()));
        }
        Member entity = cloneWithRelationships(value);
        members.add(entity);
    }

    public synchronized void updateMember(Member value) throws IllegalArgumentException {
        Member entity = cloneWithRelationships(value);
        if (members.removeIf(member -> member.getId().equals(value.getId()))) {
            members.add(entity);
        } else {
            throw new IllegalArgumentException("The member with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deleteMember(UUID id) throws IllegalArgumentException {
        if (!members.removeIf(member -> member.getId().equals(id))) {
            throw new IllegalArgumentException("The member with id \"%s\" does not exist".formatted(id));
        }
    }

    public synchronized List<Gym> findAllGyms() {
        return gyms.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createGym(Gym value) throws IllegalArgumentException {
        if (gyms.stream().anyMatch(gym -> gym.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The gym id \"%s\" is not unique".formatted(value.getId()));
        }
        gyms.add(cloningUtility.clone(value));
    }

    public synchronized void updateGym(Gym value) throws IllegalArgumentException {
        if (gyms.removeIf(gym -> gym.getId().equals(value.getId()))) {
            gyms.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The gym with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deleteGym(UUID id) throws IllegalArgumentException {
        if (!gyms.removeIf(gym -> gym.getId().equals(id))) {
            throw new IllegalArgumentException("The gym with id \"%s\" does not exist".formatted(id));
        }
    }

    private Member cloneWithRelationships(Member value) {
        Member entity = cloningUtility.clone(value);

        if (entity.getTrainer() != null) {
            Trainer trainer = trainers.stream()
                    .filter(t -> t.getId().equals(value.getTrainer().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No trainer with id \"%s\".".formatted(value.getTrainer().getId())));

            entity.setTrainer(trainer);

            if (trainer.getMembers() == null) {
                trainer.setMembers(new ArrayList<Member>());
            }
            trainer.getMembers().add(entity);
        }

        if (entity.getGym() != null) {
            Gym gym = gyms.stream()
                    .filter(g -> g.getId().equals(value.getGym().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No gym with id \"%s\".".formatted(value.getGym().getId())));


            entity.setGym(gym);

        }

        return entity;
    }

}

