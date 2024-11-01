package com.example.gymapp.gym.repository.api;

import com.example.gymapp.gym.entity.Gym;
import com.example.gymapp.member.entity.Member;
import com.example.gymapp.repository.api.Repository;

import java.util.Optional;
import java.util.UUID;

public interface GymRepository extends Repository<Gym, UUID> {
    Optional<Gym> findByName(String name);
}
