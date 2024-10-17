package com.example.gymapp.member.repository.memory;

import com.example.gymapp.datastore.component.DataStore;
import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.member.repository.api.MemberRepository;
import com.example.gymapp.trainer.entity.Trainer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class MemberInMemoryRepository implements MemberRepository {

    private final DataStore store;

    public MemberInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public List<Member> findAllByTrainer(Trainer trainer) {
        return store.findAllMembers().stream()
                .filter(member -> trainer.equals(member.getTrainer()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Member> find(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Member> findAll() {
        return List.of();
    }

    @Override
    public void create(Member entity) {

    }

    @Override
    public void delete(Member entity) {

    }

    @Override
    public void update(Member entity) {

    }



}

